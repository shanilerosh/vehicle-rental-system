package com.easycar.spring.service.imp;

import com.easycar.spring.dto.PaymentDTO;
import com.easycar.spring.entity.*;
import com.easycar.spring.repo.*;
import com.easycar.spring.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;


@Service
@Transactional
public class PaymentServiceImp implements PaymentService {

    @Autowired
    CarRepo carRepo;

    @Autowired
    ReturnRepo returnRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    BookingDetailsRepo bookingDetailsRepo;

    @Override
    public PaymentDTO recordPayment(String rid) {
        Return rtn = returnRepo.getOne(Integer.parseInt(rid));
        BookingDetail bd = rtn.getBookingDetail();
        Car car = bd.getCar();
        Customer customer = bd.getCustomer();

        LocalDate rtnDate = rtn.getDteOfReturn().toLocalDate();
        LocalDate dteOfObtained = bd.getRqrdDateTime().toLocalDate();

        long between = DAYS.between(dteOfObtained, rtnDate);
        int numberOfDays = Math.toIntExact(between);
        int usedMilage = rtn.getMilage() - car.getMilege();
        double totalValue = 0;
        double driverCharge = 0;
        totalValue += rtn.getDamages();
        boolean dly = true;
        if (numberOfDays > 30) {
            dly = false;
            int months = Math.toIntExact(MONTHS.between(rtnDate, dteOfObtained));
            System.out.println("Months");
            double rental = car.getMnthlyRate() * months;
            totalValue += rental;
            if (usedMilage > (car.getFreeKmPerMonth() * months)) {
                int additionMilage = usedMilage - (car.getFreeKmPerMonth() * months);
                totalValue += (additionMilage * car.getPricePerExtrakm());
            }
        } else {
            System.out.println("Days");
            dly = true;
            double rental = numberOfDays * car.getDlyRate();
            totalValue += rental;
            if (usedMilage > (car.getFreeKmPerDay() * numberOfDays)) {
                int addtionlMilage = usedMilage - (car.getFreeKmPerDay() * numberOfDays);
                totalValue += (addtionlMilage * car.getPricePerExtrakm());
            }
        }

        Driver driver = bd.getDriver();
        if (driver != null) {
            driverCharge = (numberOfDays * 1_000);
            totalValue += driverCharge;
            driver.setDriverStatus("open");
        }


        bd.setStatus("closed");
        car.setCarState("Availabe");
        car.setMilege(rtn.getMilage());

        bookingDetailsRepo.save(bd);
        carRepo.save(car);
        if (driver != null) {
            driverRepo.save(driver);
        }
        paymentRepo.save(new Payment(Date.valueOf(LocalDate.now()), totalValue, rtn));
        return new PaymentDTO(dly, totalValue, driverCharge, numberOfDays, customer.getName(), car.getRegistrationNumb(), LocalDate.now().toString());
    }
}
