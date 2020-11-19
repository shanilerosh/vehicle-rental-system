package com.easycar.spring.controller;


import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.entity.Car;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.service.CarService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/car")
public class CarController {

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private CarService carService;

    @PostMapping(path = "/savecar")
    public ResponseEntity addCar(@RequestParam("carName") String carName,@RequestParam("carType") String carType,
                                 @RequestParam("carState") String carState, @RequestParam("carBrand") String carBrand, @RequestParam("carRegNumber") String carRegNumber,
                                 @RequestParam("carMonthyRate") String carMonthyRate, @RequestParam("carDailyRate") String carDailyRate,
                                 @RequestParam("carFreeKmPerDay") String carFreeKmPerDay, @RequestParam("carFreeKmPerMonth") String carFreeKmPerMonth,
                                 @RequestParam("carPricePerExtraKm") String carPricePerExtraKm, @RequestParam("carNumberOfPassenger") String carNumberOfPassenger,
                                 @RequestParam("carColor") String carColor, @RequestParam("carImageInterior") MultipartFile carImageInterior,
                                 @RequestParam("carImageFront") MultipartFile carImageFront,@RequestParam("carImageSide") MultipartFile carImageSide,@RequestParam("carImageBack") MultipartFile carImageBack,
                                @RequestParam("transmissionType") String transmissionType, @RequestParam("fuelType") String fuelType
    ){

        Object[] dataSet = {
                carName, carBrand, carRegNumber, Double.parseDouble(carMonthyRate), Double.parseDouble(carDailyRate), Integer.parseInt(carFreeKmPerDay),
                Integer.parseInt(carFreeKmPerMonth), Double.parseDouble(carPricePerExtraKm), Integer.parseInt(carNumberOfPassenger), carColor, carImageInterior, carImageFront,
                carImageSide, carImageBack,carType,carState,transmissionType,fuelType
        };
        carService.addCar(dataSet);
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAll(){
        List<CarDTO> allCars = carService.getAllCars();
        return new ResponseEntity(new StandardResponse(200,"Success",allCars),HttpStatus.CREATED);
    }

    @GetMapping(path = "/{reg}")
    public ResponseEntity findCarByReg(@PathVariable String reg){
        System.out.println(reg);
        CarDTO carByReg = carService.getCarByReg(reg);
        return new ResponseEntity(new StandardResponse(200,"Success",carByReg),HttpStatus.CREATED);
    }


    @GetMapping(path = "/getbucket/{dataset}")
    public ResponseEntity findCarToBucket(@PathVariable String reg){
        System.out.println(reg);
        return new ResponseEntity(new StandardResponse(200,"Success",carService.getCarByPk(reg)),HttpStatus.CREATED);
    }
}
