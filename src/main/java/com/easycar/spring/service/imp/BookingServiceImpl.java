package com.easycar.spring.service.imp;

import com.easycar.spring.dto.BookingDetailDTO;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        String modifiedCustDate = dateAndTime.replace("T", " ").concat(":00");
        Timestamp custDateTime = Timestamp.valueOf(modifiedCustDate);
        Timestamp bookingDateTime = Timestamp.valueOf(LocalDateTime.now());
        System.out.println("before time");
        BookingDetail bookingDetail = new BookingDetail("pending", slippath, custDateTime, bookingDateTime, location);
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
            String dateTime = bookingDetail.getDateOkBooking().toLocalDateTime().toString().replace("T", " ").substring(0,19);
            String btStatus = bookingDetail.getStatus();
            pending.add(new BookingDetailDTO(detailId,dateTime,btStatus,car,driver));
        }
        return pending;
    }
}
