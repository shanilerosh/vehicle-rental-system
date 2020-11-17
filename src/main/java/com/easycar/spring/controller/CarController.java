package com.easycar.spring.controller;


import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.entity.Car;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.service.CarService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/car")
public class CarController {

    @Autowired
    private CarRepo carRepo;
    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car) {
        carRepo.save(car);
        return new ResponseEntity(new StandardResponse(200,"Success",null), HttpStatus.CREATED);
    }
}
