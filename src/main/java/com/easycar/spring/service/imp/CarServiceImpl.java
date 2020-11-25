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
        Integer nmberOfPssngers = (Integer) dataSet[8];
        String color = (String) dataSet[9];
        MultipartFile interiorImge = (MultipartFile) dataSet[10];
        MultipartFile frntImge = (MultipartFile) dataSet[11];
        MultipartFile sideImge = (MultipartFile) dataSet[12];
        MultipartFile bckImge = (MultipartFile) dataSet[13];
        String carType = (String) dataSet[14];
        String carState = (String) dataSet[15];
        String transmissionType = (String) dataSet[16];
        String fuelType = (String) dataSet[17];
        System.out.println(fuelType + " " + dataSet[17] + " " + dataSet[18] + "" + dataSet[19]);
        int deposit = Integer.parseInt(dataSet[18].toString());
        int milage = Integer.parseInt(dataSet[19].toString());
        String interrPath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + interiorImge.getOriginalFilename();
        String frntPath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + frntImge.getOriginalFilename();
        String sidePath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + sideImge.getOriginalFilename();
        String backPath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + bckImge.getOriginalFilename();

        if (name.trim().length() == 0) {
            throw new RuntimeException("Car Name is not there ⚠");
        }
        if (brand.trim().length() == 0) {
            throw new RuntimeException("Car brand is not there ⚠");
        }
        if (reg.trim().length() == 0) {
            throw new RuntimeException("Car Registration is not there ⚠");
        }
        if (mnthlyRate.toString().trim().length() == 0) {
            throw new RuntimeException("Monthly Rate is not there ⚠");
        }
        if (dlyRate.toString().trim().length() == 0) {
            throw new RuntimeException("Daily is not there ⚠");
        }
        if (freeKmPerDay.toString().trim().length() == 0) {
            throw new RuntimeException("Free Km Per Day is not there ⚠");
        }
        if (freeKmPerMonth.toString().trim().length() == 0) {
            throw new RuntimeException("Free Km Per Month is not there ⚠");
        }
        if (pricePerExtrakm.toString().trim().length() == 0) {
            throw new RuntimeException("Price per extra km is not there ⚠");
        }
        if (color.toString().trim().length() == 0) {
            throw new RuntimeException("Color is not there ⚠");
        }
        if (nmberOfPssngers.toString().trim().length() == 0) {
            throw new RuntimeException("Number of passengrs is not there ⚠");
        }
        if (interiorImge.getOriginalFilename() == "") {
            throw new RuntimeException("Interioir image is not there ⚠");
        }
        if (frntImge.getOriginalFilename() == "") {
            throw new RuntimeException("Front image is not there ⚠");
        }
        if (bckImge.getOriginalFilename() == "") {
            throw new RuntimeException("Back image is not there ⚠");
        }
        if (sideImge.getOriginalFilename() == "") {
            throw new RuntimeException("Side image is not there ⚠");
        }
        if (Integer.toString(deposit).trim().length() == 0) {
            throw new RuntimeException("Deposit is not there ⚠");
        }
        if (Integer.toString(milage).trim().length() == 0) {
            throw new RuntimeException("Milage is not there ⚠");
        }

        System.out.println("File name " + interiorImge.getOriginalFilename());
        Car carByRegistrationNumb = carRepo.findCarsByRegistrationNumb(reg);
        if (carByRegistrationNumb != null) {
            throw new RuntimeException("Car with the Registration Number " + reg + " already exists.Please try again later");
        }
        try {
            interiorImge.transferTo(new File(interrPath));
            frntImge.transferTo(new File(frntPath));
            sideImge.transferTo(new File(sidePath));
            bckImge.transferTo(new File(backPath));
        } catch (IOException e) {

        }
        Car car = new Car(name, brand, carType, reg, mnthlyRate, dlyRate, freeKmPerDay, freeKmPerMonth, pricePerExtrakm, nmberOfPssngers, color, carState, interrPath, frntPath, sidePath, backPath, transmissionType, fuelType, milage, deposit);
        carRepo.save(car);
    }

    @Override
    public void updateCar(Object[] dataSet) {
        String name = (String) dataSet[0];
        String brand = (String) dataSet[1];
        String reg = (String) dataSet[2];
        Double mnthlyRate = (Double) dataSet[3];
        Double dlyRate = (Double) dataSet[4];
        Integer freeKmPerDay = (Integer) dataSet[5];
        Integer freeKmPerMonth = (Integer) dataSet[6];
        Double pricePerExtrakm = (Double) dataSet[7];
        Integer nmberOfPssngers = (Integer) dataSet[8];
        String color = (String) dataSet[9];
        MultipartFile interiorImge = (MultipartFile) dataSet[10];
        MultipartFile frntImge = (MultipartFile) dataSet[11];
        MultipartFile sideImge = (MultipartFile) dataSet[12];
        MultipartFile bckImge = (MultipartFile) dataSet[13];
        String carType = (String) dataSet[14];
        String carState = (String) dataSet[15];
        String transmissionType = (String) dataSet[16];
        String fuelType = (String) dataSet[17];
        System.out.println(fuelType + " " + dataSet[17] + " " + dataSet[18] + "" + dataSet[19]);
        int deposit = Integer.parseInt(dataSet[18].toString());
        int milage = Integer.parseInt(dataSet[19].toString());
        String interrPath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + interiorImge.getOriginalFilename();
        String frntPath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + frntImge.getOriginalFilename();
        String sidePath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + sideImge.getOriginalFilename();
        String backPath = "/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/car/" + bckImge.getOriginalFilename();

        if (name.trim().length() == 0) {
            throw new RuntimeException("Car Name is not there ⚠");
        }
        if (brand.trim().length() == 0) {
            throw new RuntimeException("Car brand is not there ⚠");
        }
        if (reg.trim().length() == 0) {
            throw new RuntimeException("Car Registration is not there ⚠");
        }
        if (mnthlyRate.toString().trim().length() == 0) {
            throw new RuntimeException("Monthly Rate is not there ⚠");
        }
        if (dlyRate.toString().trim().length() == 0) {
            throw new RuntimeException("Daily is not there ⚠");
        }
        if (freeKmPerDay.toString().trim().length() == 0) {
            throw new RuntimeException("Free Km Per Day is not there ⚠");
        }
        if (freeKmPerMonth.toString().trim().length() == 0) {
            throw new RuntimeException("Free Km Per Month is not there ⚠");
        }
        if (pricePerExtrakm.toString().trim().length() == 0) {
            throw new RuntimeException("Price per extra km is not there ⚠");
        }
        if (color.trim().length() == 0) {
            throw new RuntimeException("Color is not there ⚠");
        }
        if (nmberOfPssngers.toString().trim().length() == 0) {
            throw new RuntimeException("Number of passengrs is not there ⚠");
        }
        if (Integer.toString(deposit).trim().length() == 0) {
            throw new RuntimeException("Deposit is not there ⚠");
        }
        if (Integer.toString(milage).trim().length() == 0) {
            throw new RuntimeException("Milage is not there ⚠");
        }

        Car searchedCar = carRepo.findCarsByRegistrationNumb(reg);

        if (searchedCar == null) {
            throw new RuntimeException("This is not an update.This reg Number doesn't exist.Either add this as a new car or check your registration Number");
        }

        searchedCar.setName(name);
        searchedCar.setBrand(brand);
        searchedCar.setMnthlyRate(mnthlyRate);
        searchedCar.setDlyRate(dlyRate);
        searchedCar.setFreeKmPerDay(freeKmPerDay);
        searchedCar.setFreeKmPerMonth(freeKmPerDay);
        searchedCar.setPricePerExtrakm(pricePerExtrakm);
        searchedCar.setColor(color);
        searchedCar.setCarState(carState);
        searchedCar.setCarType(carType);
        searchedCar.setFuelType(fuelType);
        searchedCar.setDeposit(deposit);
        searchedCar.setMilege(milage);

        if (interiorImge.getOriginalFilename() != "") {
            try {
                System.out.println("Internal name " + interiorImge.getOriginalFilename());
                interiorImge.transferTo(new File(interrPath));
                searchedCar.setInteriorImge(interrPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (frntImge.getOriginalFilename() != "") {
            try {
                frntImge.transferTo(new File(frntPath));
                searchedCar.setFrntImg(frntPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bckImge.getOriginalFilename() != "") {
            try {
                bckImge.transferTo(new File(backPath));
                searchedCar.setBckImg(backPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (sideImge.getOriginalFilename() != "") {
            try {
                sideImge.transferTo(new File(sidePath));
                searchedCar.setSideImg(sidePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        carRepo.save(searchedCar);
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> all = carRepo.findAll();
        return mapper.map(all, new TypeToken<List<CarDTO>>() {
        }.getType());
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
        } else if (selected.equals("Passengers")) {
            int i = Integer.parseInt(custInput);
            availabe = carRepo.findAllByCarStateAndNmberOfPssngersStartingWith("Availabe", i);
        } else if (selected.equals("Fuel Type")) {
            availabe = carRepo.findAllByCarStateAndFuelTypeStartingWith("Availabe", custInput);
        } else if (selected.equals("Tranmission Type")) {
            availabe = carRepo.findAllByCarStateAndTransmissionTypeStartingWith("Availabe", custInput);
        }
        return mapper.map(availabe, new TypeToken<List<CarDTO>>() {
        }.getType());
    }

    @Override
    public List<CarDTO> searchAllTypeCarsWhenTyping(String selected, String custInput) {
        ArrayList<Car> list = null;
        if (selected.equals("Brand")) {
            list = carRepo.findAllByBrandStartingWith(custInput);
        } else if (selected.equals("Type")) {
            list = carRepo.findAllByCarTypeStartingWith(custInput);
        } else if (selected.equals("Fuel")) {
            list = carRepo.findAllByFuelTypeStartingWith(custInput);
        } else if (selected.equals("Transmission")) {
            list = carRepo.findAllByFuelTypeStartingWith(custInput);
        }
        return mapper.map(list, new TypeToken<List<CarDTO>>() {
        }.getType());
    }
}
