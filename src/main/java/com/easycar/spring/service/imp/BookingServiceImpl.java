package com.easycar.spring.service.imp;

import com.easycar.spring.dto.*;
import com.easycar.spring.entity.BookingDetail;
import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.Driver;
import com.easycar.spring.repo.BookingDetailsRepo;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.DriverRepo;
import com.easycar.spring.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {


    @Autowired
    ModelMapper mapper;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    BookingDetailsRepo bookingDetailsRepo;

    @Autowired
    DriverRepo driverRepo;

    Random random=new Random();

    @Override
    public void placeBooking(Object[] bookingDetailData) {
        String location= (String) bookingDetailData[0];
        String dateAndTime= (String) bookingDetailData[1];
        String driver= (String) bookingDetailData[2];
        String vhcleId= (String) bookingDetailData[3];
        MultipartFile slip = (MultipartFile) bookingDetailData[4];
        String customer= (String) bookingDetailData[5];
        String returnDateTime= (String) bookingDetailData[6];

        //Find customer
        Optional<Customer> foundCust = customerRepo.findById(customer);
        Customer orderCustomer = foundCust.get();
        //Find Car
        System.out.println("Car reg number "+vhcleId);
        Car orderCar = carRepo.findCarsByRegistrationNumb(vhcleId);
        System.out.println("Car brand"+orderCar.getBrand());
        if(orderCar==null){
            throw new RuntimeException("How this is possible");
        }

        Driver orderDriver=null;
        if(Boolean.parseBoolean(driver)) {
            List<Driver> listOfDrivers = driverRepo.findAll();

            int randNumber = random.nextInt(listOfDrivers.size());
            orderDriver = listOfDrivers.get(randNumber);
        }else {
            orderDriver=null;
        }

        //Saving the slip
        String slippath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car"+slip.getOriginalFilename();
        System.out.println("Before slip path");
        try {
            slip.transferTo(new File(slippath));
        } catch (IOException e) {

        }
        System.out.println("Before time");
        Date custDateTime = Date.valueOf(dateAndTime);
        Date reutnrDate = Date.valueOf(returnDateTime);
        Date bookigDate = Date.valueOf(LocalDate.now());
        System.out.println("Comes here");
        System.out.println(dateAndTime+" "+returnDateTime+" "+bookigDate);
        BookingDetail bookingDetail = new BookingDetail("pending", slippath, custDateTime, bookigDate,reutnrDate,location);
        bookingDetail.setCustomer(orderCustomer);
        bookingDetail.setDriver(orderDriver);
        bookingDetail.setCar(orderCar);
        bookingDetailsRepo.save(bookingDetail);
    }

    @Override
    public List<BookingDetailDTO> getDetailsOnStatus(String custEmail,String status) {
        Customer loggedInCustomer = customerRepo.getOne(custEmail);
        List<BookingDetail> booking = bookingDetailsRepo.getAllByCustomerAndStatus(loggedInCustomer,status);

        ArrayList<BookingDetailDTO> pending = new ArrayList<>();
        for (BookingDetail bookingDetail : booking) {
            Car car = bookingDetail.getCar();
            int detailId = bookingDetail.getDetailId();
            Driver driver = bookingDetail.getDriver();
            System.out.println("Date of booking "+bookingDetail.getDateOkBooking());
            String dateTime = bookingDetail.getDateOkBooking().toString();
            String btStatus = bookingDetail.getStatus();
            pending.add(new BookingDetailDTO(detailId,dateTime,btStatus,car,driver));
        }
        return pending;
    }

    @Override
    public List<BookingPendingDTO> getPendingBooking(String status) {
        List<BookingDetail> allByStatus = bookingDetailsRepo.getAllByStatus(status);
        ArrayList<BookingPendingDTO> pendings = new ArrayList<>();
        for (BookingDetail byStatus : allByStatus) {
            String custName = byStatus.getCustomer().getName();
            System.out.println("Inside lloop");
            Driver driverStat = byStatus.getDriver();
            String driver = driverStat==null ? "N/A" : driverStat.getDid();
            int detailId = byStatus.getDetailId();
            String rqrdLocation = byStatus.getRqrdLocation();
            String dteOfBooking = byStatus.getDateOkBooking().toString();
            String dteOfReturn = byStatus.getDateOkBooking().toString();
            String status1 = byStatus.getStatus();
            System.out.println("here");
            pendings.add(new BookingPendingDTO(detailId,custName,rqrdLocation,dteOfBooking,dteOfReturn,driver,status1));
        }
        return pendings;

    }

    @Override
    public List<DriverScheduleDTO> getDriverSchedule(String did, String status,String from,String to) {
            Optional<Driver> byId = driverRepo.findById(did);
            if(!byId.isPresent()) throw new RuntimeException("Error,Driver is not there");
        Driver driver = byId.get();
        Date dteFrom = Date.valueOf(from);
        Date dteTo = Date.valueOf(to);
        List<BookingDetail> list = bookingDetailsRepo.findAllByRqrdDateTimeGreaterThanEqualAndDateOfReturn(dteFrom,dteTo);
        ArrayList<DriverScheduleDTO> driverScheduleDTO = new ArrayList<>();
        for (BookingDetail bookingDetail : list) {
            String dteOfBooking = bookingDetail.getDateOkBooking().toString();
            String dteOfReturn = bookingDetail.getDateOfReturn().toString();
            if(bookingDetail.getDriver()!=null){
                if (bookingDetail.getDriver().getDid().equals(driver.getDid()) && bookingDetail.getStatus().equals(status)) {
                    System.out.println("INsider loop");
                    driverScheduleDTO.add(new DriverScheduleDTO(
                            bookingDetail.getDriver().getDid(),
                            bookingDetail.getDriver().getDriverName(),
                            dteOfBooking,
                            dteOfReturn,
                            bookingDetail.getRqrdLocation()
                    ));
                }
            }
        }
        return driverScheduleDTO;
    }

    @Override
    public void finalizeBooking(String bid, String did) {
        Optional<BookingDetail> byId = bookingDetailsRepo.findById(Integer.parseInt(bid));
        BookingDetail bookingDetail = byId.get();
        System.out.println("here");
        Driver one = driverRepo.getOne(did);
        bookingDetail.setDriver(one);
        bookingDetail.setStatus("open");
        BookingDetail save = bookingDetailsRepo.save(bookingDetail);
    }

    @Override
    public void finalizeBookingWithoutDriver(String bid) {
        Optional<BookingDetail> byId = bookingDetailsRepo.findById(Integer.parseInt(bid));
        BookingDetail bookingDetail = byId.get();
        bookingDetail.setStatus("open");
        bookingDetailsRepo.save(bookingDetail);
    }

    @Override
    public void denyBooking(String bid, String denialMsg) {
        Optional<BookingDetail> byId = bookingDetailsRepo.findById(Integer.parseInt(bid));
        BookingDetail bookingDetail = byId.get();
        bookingDetail.setRemarks(denialMsg);
        bookingDetail.setStatus("denied");
        bookingDetailsRepo.save(bookingDetail);
    }

    @Override
    public List<CarScheduleDTO> getCarSchedule(String carId) {
        Optional<Car> byId = carRepo.findById(Integer.parseInt(carId));
        Car car = byId.get();
        List<BookingDetail> allByCar = bookingDetailsRepo.findAllByCar(car);
        ArrayList<CarScheduleDTO> carScheduleDTOS = new ArrayList<>();
        for (BookingDetail bookingDetail : allByCar) {
            carScheduleDTOS.add(new CarScheduleDTO(
                    bookingDetail.getRqrdDateTime().toString(),
                    bookingDetail.getDateOfReturn().toString(),
                    bookingDetail.getDriver()==null ? "No Driver":bookingDetail.getDriver().getDriverName()
            ));
        }
        System.out.println(carScheduleDTOS.size());
        return carScheduleDTOS;
    }

    @Override
    public PaymentDetailDTO getPaymentDetail(String bid) {
        Optional<BookingDetail> byId = bookingDetailsRepo.findById(Integer.parseInt(bid));
        if(!byId.isPresent()){
            throw new RuntimeException("Wrong Booking Id,Try again");
        }
        BookingDetail bookingDetail = byId.get();
        Driver driver = bookingDetail.getDriver();
        Car car = bookingDetail.getCar();
        Customer customer = bookingDetail.getCustomer();
        PaymentDetailDTO paymentDetailDTO = new PaymentDetailDTO(bookingDetail.getDetailId(), bookingDetail.getRqrdDateTime().toString(), bookingDetail.getStatus(), car, driver,customer);
        return paymentDetailDTO;
    }
}
