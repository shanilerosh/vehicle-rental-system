package com.easycar.spring.service.imp;

import com.easycar.spring.dto.ReturnDTO;
import com.easycar.spring.entity.BookingDetail;
import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Return;
import com.easycar.spring.repo.BookingDetailsRepo;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.repo.ReturnRepo;
import com.easycar.spring.service.ReturnService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class ReturnServiceImpl implements ReturnService {


    @Autowired
    ModelMapper mapper;

    @Autowired
    ReturnRepo returnRepo;

    @Autowired
    CarRepo carRepo;

    @Autowired
    BookingDetailsRepo bookingDetailsRepo;

    @Override
    public void calculatePaymentAndReturn(String[] data) {
        System.out.println("here");
        Date rtnDate = Date.valueOf(data[0]);
        Date strtDate = Date.valueOf(data[1]);
        int damage = Integer.parseInt(data[2]);
        int startMilage = Integer.parseInt(data[3]);
        int endMilage = Integer.parseInt(data[4]);
        boolean driver = Boolean.parseBoolean(data[5]);
        String crId = data[6];
        Car car = carRepo.getOne(Integer.parseInt(crId));
        LocalDate rtnLocal = rtnDate.toLocalDate();
        LocalDate stnLocal = strtDate.toLocalDate();
        System.out.println("here before days");
        Integer days = Math.toIntExact(DAYS.between(stnLocal, rtnLocal));
        double totalValue=0;
        int userMilage=endMilage-startMilage;
        System.out.println("here before days > 30");
        if(days>30){
            totalValue+=(days*car.getDlyRate());
            int dlyFreeKm=car.getFreeKmPerDay()*days;
            if(userMilage>dlyFreeKm){
                double additional = (userMilage - dlyFreeKm) * car.getPricePerExtrakm();
                totalValue+=additional;
            }
        }else {
            int months=days/30;
            totalValue+=(days*car.getMnthlyRate());
            int monthlyFreeKm=car.getFreeKmPerMonth()*months;
            if(userMilage>monthlyFreeKm){
                double additional = (userMilage - monthlyFreeKm) * car.getPricePerExtrakm();
                totalValue += additional;
            }
        }
        totalValue += damage;

        if (driver) {
            totalValue += (days * 1000);
        }
        System.out.println(totalValue);
    }

    @Override
    public ReturnDTO saveReturn(ReturnDTO dto) {
        Date date = Date.valueOf(LocalDate.parse(dto.getDteOfReturn().toString()));
        BookingDetail bd = bookingDetailsRepo.getOne(Integer.parseInt(dto.getBid()));

        if (bd.getRqrdDateTime().after(date)) {
            throw new RuntimeException("Error return date cannot be before the obtained date");
        }
        Return save = returnRepo.save(new Return(dto.getMilage(), date, dto.getDamages(), bd));
        return new ReturnDTO(save.getRid(), Integer.toString(bd.getDetailId()), dto.getDamages(), dto.getMilage(), dto.getDteOfReturn());
    }
}
