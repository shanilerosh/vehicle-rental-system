package com.easycar.spring.controller;

import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.dto.DriverDTO;
import com.easycar.spring.service.DriverService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("api/v1/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping
    public ResponseEntity addDriver(@RequestBody DriverDTO driverDTO) {
        driverService.addDriver(driverDTO);
        return new ResponseEntity(new StandardResponse(200,"Success",null), HttpStatus.CREATED);
    }


    @GetMapping("/getdrivernames/{except}")
    public ResponseEntity getDriver(@PathVariable String except) {
        System.out.println("Inside driver controller");
        ArrayList<DriverDTO> driverName = driverService.findDriverNamesExcept(except);
        System.out.println("Inside");
        return new ResponseEntity(new StandardResponse(200, "Success", driverName), HttpStatus.CREATED);
    }

    @GetMapping("/availableAndOccupied")
    public ResponseEntity getDriver() {
        ArrayList<Integer> avlOcc = driverService.countAvlAndOccupied();
        return new ResponseEntity(new StandardResponse(200, "Success", avlOcc), HttpStatus.CREATED);
    }
}

