package com.easycar.spring.controller;


import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.entity.Car;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.service.CarService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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
    public ResponseEntity addCar(@RequestParam("carName") String carName, @RequestParam("carType") String carType,
                                 @RequestParam("carState") String carState, @RequestParam("carBrand") String carBrand, @RequestParam("carRegNumber") String carRegNumber,
                                 @RequestParam("carMonthyRate") String carMonthyRate, @RequestParam("carDailyRate") String carDailyRate,
                                 @RequestParam("carFreeKmPerDay") String carFreeKmPerDay, @RequestParam("carFreeKmPerMonth") String carFreeKmPerMonth,
                                 @RequestParam("carPricePerExtraKm") String carPricePerExtraKm, @RequestParam("carNumberOfPassenger") String carNumberOfPassenger,
                                 @RequestParam("carColor") String carColor, @RequestParam("carImageInterior") MultipartFile carImageInterior,
                                 @RequestParam("carImageFront") MultipartFile carImageFront, @RequestParam("carImageSide") MultipartFile carImageSide, @RequestParam("carImageBack") MultipartFile carImageBack,
                                 @RequestParam("transmissionType") String transmissionType, @RequestParam("fuelType") String fuelType,
                                 @RequestParam("carDeposit") String carDeposit, @RequestParam("carMilage") String carMilage
    ) {

        Object[] dataSet = {
                carName, carBrand, carRegNumber, Double.parseDouble(carMonthyRate), Double.parseDouble(carDailyRate), Integer.parseInt(carFreeKmPerDay),
                Integer.parseInt(carFreeKmPerMonth), Double.parseDouble(carPricePerExtraKm), Integer.parseInt(carNumberOfPassenger), carColor, carImageInterior, carImageFront,
                carImageSide, carImageBack, carType, carState, transmissionType, fuelType, carDeposit, carMilage};
        carService.addCar(dataSet);
        return new ResponseEntity(new StandardResponse(200, "Success", null), HttpStatus.CREATED);
    }


    @PostMapping(path = "/updatecar")
    public ResponseEntity updateCar(@RequestParam("carName") String carName, @RequestParam("carType") String carType,
                                    @RequestParam("carState") String carState, @RequestParam("carBrand") String carBrand, @RequestParam("carRegNumber") String carRegNumber,
                                    @RequestParam("carMonthyRate") String carMonthyRate, @RequestParam("carDailyRate") String carDailyRate,
                                    @RequestParam("carFreeKmPerDay") String carFreeKmPerDay, @RequestParam("carFreeKmPerMonth") String carFreeKmPerMonth,
                                    @RequestParam("carPricePerExtraKm") String carPricePerExtraKm, @RequestParam("carNumberOfPassenger") String carNumberOfPassenger,
                                    @RequestParam("carColor") String carColor, @RequestParam("carImageInterior") MultipartFile carImageInterior,
                                    @RequestParam("carImageFront") MultipartFile carImageFront, @RequestParam("carImageSide") MultipartFile carImageSide, @RequestParam("carImageBack") MultipartFile carImageBack,
                                    @RequestParam("transmissionType") String transmissionType, @RequestParam("fuelType") String fuelType,
                                    @RequestParam("carDeposit") String carDeposit, @RequestParam("carMilage") String carMilage
    ) {
        Object[] dataSet = {
                carName, carBrand, carRegNumber, Double.parseDouble(carMonthyRate), Double.parseDouble(carDailyRate), Integer.parseInt(carFreeKmPerDay),
                Integer.parseInt(carFreeKmPerMonth), Double.parseDouble(carPricePerExtraKm), Integer.parseInt(carNumberOfPassenger), carColor, carImageInterior, carImageFront,
                carImageSide, carImageBack, carType, carState, transmissionType, fuelType, carDeposit, carMilage};
        carService.updateCar(dataSet);
        return new ResponseEntity(new StandardResponse(200, "Success", null), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity getAll() {
        List<CarDTO> allCars = carService.getAllCars();
        return new ResponseEntity(new StandardResponse(200, "Success", allCars), HttpStatus.CREATED);
    }


    @GetMapping(path = "/filter/{selected}/{custInput}")
    public ResponseEntity getAll(@PathVariable String selected,@PathVariable String custInput){
        List<CarDTO> allCars = carService.getAllCarsWhenTyping(selected,custInput);
        return new ResponseEntity(new StandardResponse(200,"Success",allCars),HttpStatus.CREATED);
    }

    @GetMapping(path = "/{reg}")
    public ResponseEntity findCarByReg(@PathVariable String reg){
        System.out.println(reg);
        CarDTO carByReg = carService.getCarByReg(reg);
        return new ResponseEntity(new StandardResponse(200,"Success",carByReg),HttpStatus.CREATED);
    }


    @GetMapping(path = "/getbucket/{reg}")
    public ResponseEntity findCarToBucket(@PathVariable String reg){
        System.out.println(reg);
        return new ResponseEntity(new StandardResponse(200,"Success",carService.getCarByPk(reg)),HttpStatus.CREATED);
    }

    @GetMapping(path = "/getcarbystate/{state}")
    public ResponseEntity findCarByState(@PathVariable String state) {
        ArrayList<CarDTO> list = carService.getCarByState(state);
        return new ResponseEntity(new StandardResponse(200, "Success", list), HttpStatus.CREATED);
    }

    @GetMapping(path = "/updatestate/{crId}/{val}")
    public ResponseEntity findCarByState(@PathVariable String crId, @PathVariable String val) {
        carService.updateState(crId, val);
        return new ResponseEntity(new StandardResponse(200, "Success", null), HttpStatus.CREATED);
    }

    @GetMapping(path = "/searchCars/{selected}/{custInput}")
    public ResponseEntity searchCarWithUserInput(@PathVariable String selected, @PathVariable String custInput) {
        List<CarDTO> allCars = carService.searchAllTypeCarsWhenTyping(selected, custInput);
        return new ResponseEntity(new StandardResponse(200, "Success", allCars), HttpStatus.CREATED);
    }

    @GetMapping(path = "/availableAndReserved")
    public ResponseEntity findAvlbleAndReserver() {
        ArrayList<Integer> list = carService.getAvailbleAndReserved();
        return new ResponseEntity(new StandardResponse(200, "Success", list), HttpStatus.CREATED);
    }

    @GetMapping(path = "/countcarsformaintainance")
    public ResponseEntity findCarsForMain() {
        Integer count = carService.findCarsForMaintainance();
        return new ResponseEntity(new StandardResponse(200, "Success", count), HttpStatus.CREATED);
    }


    @GetMapping(path = "/findcarsformaintainance")
    public ResponseEntity findCarForMain() {
        return new ResponseEntity(new StandardResponse(200, "Success", null), HttpStatus.CREATED);
    }


}
