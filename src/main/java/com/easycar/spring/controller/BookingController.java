package com.easycar.spring.controller;

import com.easycar.spring.dto.BookingDTO;
import com.easycar.spring.service.BookingService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping(params = {"date","email"})
    public ResponseEntity addBooking(String date,String email) {
        bookingService.addBooking(date, email);
        return new ResponseEntity(new StandardResponse(200,"Success",null), HttpStatus.CREATED);
    }

}
