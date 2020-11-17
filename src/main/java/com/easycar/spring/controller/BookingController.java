package com.easycar.spring.controller;

import com.easycar.spring.dto.BookingDTO;
import com.easycar.spring.entity.Booking;
import com.easycar.spring.entity.BookingDetail;
import com.easycar.spring.repo.BookingDetailsRepo;
import com.easycar.spring.repo.BookingRepo;
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
    private BookingService bookingService;

    @Autowired
    private BookingRepo repo;

    @Autowired
    private BookingDetailsRepo detailsRepo;

    @PostMapping()
    public ResponseEntity addBooking(@RequestBody Booking booking) {
        System.out.println("here");
        System.out.println(booking);
        repo.save(booking);
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }

    @PostMapping(path = "/bookingdetail")
    public ResponseEntity addBooking(@RequestBody BookingDetail bookingDetail){
        System.out.println(bookingDetail);
        detailsRepo.save(bookingDetail);
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }

}
