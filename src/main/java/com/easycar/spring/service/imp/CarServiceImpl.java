package com.easycar.spring.service.imp;

import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.CarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepo carRepo;

    @Autowired
    ModelMapper mapper;


    @Override
    public void addCar(Object[] dataSet) {
        String name= (String) dataSet[0];
        String brand= (String) dataSet[1];
        String reg= (String) dataSet[2];
        Double mnthlyRate = (Double) dataSet[3];
        Double dlyRate = (Double) dataSet[4];
        Integer freeKmPerDay= (Integer) dataSet[5];
        Integer freeKmPerMonth= (Integer) dataSet[6];
        Double pricePerExtrakm = (Double) dataSet[7];
        Integer nmberOfPssngers= (Integer) dataSet[8];
        String color= (String) dataSet[9];
        MultipartFile interiorImge= (MultipartFile) dataSet[10];
        MultipartFile frntImge= (MultipartFile) dataSet[11];
        MultipartFile sideImge= (MultipartFile) dataSet[12];
        MultipartFile bckImge= (MultipartFile) dataSet[13];
        String carType= (String) dataSet[14];
        String carState= (String) dataSet[15];
        String transmissionType= (String) dataSet[16];
        String fuelType= (String) dataSet[17];
        String interrPath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/customer/"+interiorImge.getOriginalFilename();
        String frntPath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/customer/"+frntImge.getOriginalFilename();
        String sidePath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/customer/"+sideImge.getOriginalFilename();
        String backPath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/customer/"+bckImge.getOriginalFilename();

        Car carByRegistrationNumb = carRepo.findCarsByRegistrationNumb(reg);

        if(carByRegistrationNumb!=null){
            throw new RuntimeException("Car with the Registration Number "+reg+" already exists.Please try again later");
        }
        try {
            interiorImge.transferTo(new File(interrPath));
            frntImge.transferTo(new File(frntPath));
            sideImge.transferTo(new File(sidePath));
            bckImge.transferTo(new File(backPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Car car = new Car(name, brand, carType, reg, mnthlyRate, dlyRate, freeKmPerDay, freeKmPerMonth, pricePerExtrakm, nmberOfPssngers, color, carState, interrPath, frntPath, sidePath, backPath,transmissionType,fuelType);
        carRepo.save(car);
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> all = carRepo.findAll();
        return mapper.map(all,new TypeToken<List<CarDTO>>(){}.getType());
    }

    @Override
    public CarDTO getCarByReg(String reg) {
        Car carByRegistrationNumb = carRepo.findCarsByRegistrationNumb(reg);
        return mapper.map(carByRegistrationNumb,CarDTO.class);
    }

    @Override
    public CarDTO getCarByPk(String reg) {
        Car carByReg = carRepo.findCarByReg(Integer.parseInt(reg));
        System.out.println(carByReg.getBrand());
        return mapper.map(carByReg,CarDTO.class);
    }
}