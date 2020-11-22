package com.easycar.spring.controller;

import com.easycar.spring.dto.DriverDTO;
import com.easycar.spring.dto.ReturnDTO;
import com.easycar.spring.entity.Return;
import com.easycar.spring.repo.ReturnRepo;
import com.easycar.spring.service.ReturnService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/return")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @PostMapping(path = "/calpayment")
    public ResponseEntity getOnStatus(@RequestParam("returndate") String returnDate,
                                      @RequestParam("startdate") String startdate,
                                      @RequestParam("damage") String damage,@RequestParam("startmilage") String startmilage,
                                      @RequestParam("endingmilage") String endingmilage,@RequestParam("driver") String driver,@RequestParam("carId") String carId){

        String[] data={returnDate,startdate,damage,startmilage,endingmilage,driver,carId};
        returnService.calculatePaymentAndReturn(data);
        return new ResponseEntity(new StandardResponse(200,"Success",null), HttpStatus.CREATED);
    }
}
