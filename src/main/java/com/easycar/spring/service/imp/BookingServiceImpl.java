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

        if(location.trim().length()==0){
            throw new RuntimeException("Error.No Location Mentioned");
        }if(dateAndTime.trim().length()==0){
            throw new RuntimeException("Error.No Required Date Mentioned");
        }if(vhcleId.trim().length()==0){
            throw new RuntimeException("Error.No veihcle Selected");
        }if(slip==null){
            throw new RuntimeException("No desposit slip provided");
        }if(returnDateTime.trim().length()==0){
            throw new RuntimeException("No return date Included");
        }if(slip.getOriginalFilename().trim().length()==0){
            throw new RuntimeException("No Slip included.Please upload a slip");
        }

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
        if(reutnrDate.before(bookigDate) || reutnrDate.before(custDateTime)){
            throw new RuntimeException("False selection return date can't be before Return date of Today's date");
        }
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
        Car car = bookingDetail.getCar();
        car.setCarState("Occupied");
        Driver one = driverRepo.getOne(did);
        one.setDriverStatus("occupied");
        driverRepo.save(one);
        carRepo.save(car);
        bookingDetail.setDriver(one);
        bookingDetail.setStatus("open");
        BookingDetail save = bookingDetailsRepo.save(bookingDetail);
    }

    @Override
    public void finalizeBookingWithoutDriver(String bid) {
        Optional<BookingDetail> byId = bookingDetailsRepo.findById(Integer.parseInt(bid));
        BookingDetail bookingDetail = byId.get();
        Car car = bookingDetail.getCar();
        car.setCarState("occupied");
        carRepo.save(car);
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
    public BookingPendingDTO getBookingDetail(String id) {
        Optional<BookingDetail> byId = bookingDetailsRepo.findById(Integer.parseInt(id));
        BookingDetail bookingDetail = byId.get();
        return new BookingPendingDTO(bookingDetail.getDetailId(),
                bookingDetail.getCustomer().getName(),
                bookingDetail.getRqrdLocation(),
                bookingDetail.getDateOkBooking().toString(),
                bookingDetail.getDateOfReturn().toString(),
                bookingDetail.getDriver() == null ? "No Driver" : bookingDetail.getDriver().getDid(),
                bookingDetail.getStatus(),
                bookingDetail.getRemarks()
        );
    }

    @Override
    public List<BookingPendingDTO> getOpenBookingsForReturn(String selection, String value) {
        if (selection.equals("Booking")) {
            System.out.println("Inde booking");
            if (value.matches("[0-9]+")) {
                System.out.println("Inside tegex");
                ArrayList<BookingPendingDTO> list = new ArrayList<>();
                List<BookingDetail> open = bookingDetailsRepo.findAllByStatusAndDetailIdStartingWith("open", Integer.parseInt(value.trim()));
                for (BookingDetail bookingDetail : open) {
                    list.add(new BookingPendingDTO(bookingDetail.getDetailId(), bookingDetail.getCustomer().getName(),
                            bookingDetail.getRqrdDateTime().toString(), bookingDetail.getDateOkBooking().toString(),
                            bookingDetail.getDateOfReturn().toString(), bookingDetail.getDriver() == null ? "N/A" : bookingDetail.getDriver().getDid(),
                            bookingDetail.getStatus(), bookingDetail.getRemarks()));
                }
                return list;
            }
        } else if (selection.equals("CustomerName")) {
            List<Customer> allByNameStartingWith = customerRepo.findAllByNameStartingWith(value.trim());
            ArrayList<BookingPendingDTO> list = new ArrayList<>();
            for (Customer customer : allByNameStartingWith) {
                List<BookingDetail> alltheBooking = bookingDetailsRepo.findAllByCustomerAndStatus(customer, "open");
                for (BookingDetail open : alltheBooking) {
                    list.add(new BookingPendingDTO(open.getDetailId(), customer.getName(), open.getRqrdDateTime().toString(),
                            open.getDateOkBooking().toString(), open.getDateOfReturn().toString(), open.getDriver() == null ? "N/A" : open.getDriver().getDid(),
                            open.getStatus(), open.getRemarks()));
                }

            }

            return list;
        }
        return null;
    }

    @Override
    public Integer getBookingCountAsAt(LocalDate now) {
        Integer integer = bookingDetailsRepo.countByDateOkBooking(Date.valueOf(now));
        return integer;
    }

    @Override
    public Integer getActiveBooking(String open) {
        System.out.println("Open " + open);
        return bookingDetailsRepo.countByStatus(open);
    }

    @Override
    public ArrayList<DriverScheduleDTO> getIndividualSch(String did) {
        ArrayList<DriverScheduleDTO> list = new ArrayList<>();
        Driver driver = driverRepo.getOne(did);
        List<BookingDetail> open = bookingDetailsRepo.findAllByDriverAndStatus(driver, "open");

        for (BookingDetail bd : open) {
            if (bd.getDriver() != null) {
                Driver tmp = bd.getDriver();
                list.add(new DriverScheduleDTO(tmp.getDid(), tmp.getDriverName(), bd.getRqrdDateTime().toString(), bd.getDateOfReturn().toString(), bd.getRqrdLocation()));
            }
        }

        return list;
    }
}
