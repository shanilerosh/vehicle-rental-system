package com.easycar.spring.service.imp;

import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.entity.Car;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.service.CarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Integer trnsmission= (int) dataSet[18];
        String interrPath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/"+interiorImge.getOriginalFilename();
        String frntPath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/"+frntImge.getOriginalFilename();
        String sidePath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/"+sideImge.getOriginalFilename();
        String backPath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/"+bckImge.getOriginalFilename();

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
        Car car = new Car(name, brand, carType, reg, mnthlyRate, dlyRate, freeKmPerDay, freeKmPerMonth, pricePerExtrakm, nmberOfPssngers, color, carState, interrPath, frntPath, sidePath, backPath,transmissionType,fuelType,trnsmission);
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

    @Override
    public ArrayList<CarDTO> getCarByState(String state) {
        ArrayList<Car> allByCarState = carRepo.findAllByCarState(state);
        return mapper.map(allByCarState,new TypeToken<List<CarDTO>>(){}.getType());
    }

    @Override
    public void updateState(String crId, String val) {
        Optional<Car> byId = carRepo.findById(Integer.parseInt(crId));
        Car car = byId.get();
        car.setCarState(val);
        carRepo.save(car);
    }

    @Override
    public List<CarDTO> getAllCarsWhenTyping(String selected, String custInput) {
        ArrayList<Car> availabe=null;
        System.out.println(custInput);
        System.out.println(selected);
        if(selected.equals("Color")){
             availabe= carRepo.findAllByCarStateAndColorStartingWith("Availabe",custInput);
        }else if(selected.equals("Registration")){
            availabe= carRepo.findAllByCarStateAndRegistrationNumbStartingWith("Availabe",custInput);
        }else if(selected.equals("Car Type")){
            availabe= carRepo.findAllByCarStateAndCarTypeStartingWith("Availabe",custInput);
        }else if(selected.equals("Passengers")){
            int i = Integer.parseInt(custInput);
            availabe= carRepo.findAllByCarStateAndNmberOfPssngersStartingWith("Availabe",i);
        }else if(selected.equals("Fuel Type")){
            availabe= carRepo.findAllByCarStateAndFuelTypeStartingWith("Availabe",custInput);
        }else if(selected.equals("Tranmission Type")){
            availabe= carRepo.findAllByCarStateAndTransmissionTypeStartingWith("Availabe",custInput);
        }
        return mapper.map(availabe,new TypeToken<List<CarDTO>>(){}.getType());
    }
}
