package com.easycar.spring.controller;

import com.easycar.spring.dto.BookingDetailDTO;
import com.easycar.spring.repo.BookingDetailsRepo;
import com.easycar.spring.service.BookingService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(path = "/bookingdetail")
    public ResponseEntity addBooking(@RequestParam("bcktLocation") String bcktLocation, @RequestParam("bcktPckUp") String bcktPckUp,
                                     @RequestParam("driver") String driver, @RequestParam("vehicleId") String vehicleId,
                                     @RequestParam("customer") String customer,@RequestParam("bcktDoc") MultipartFile bcktDoc){
        System.out.println(driver);
        System.out.println(vehicleId);
        Object[] bookingDetailData={
            bcktLocation,
            bcktPckUp,
            driver,
            vehicleId,
            bcktDoc,
            customer
        };

        bookingService.placeBooking(bookingDetailData);
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }


    @GetMapping(path = "/getonstatus/{email}/{status}")
    public ResponseEntity  getOnStatus(@PathVariable String email,@PathVariable String status) {
        List<BookingDetailDTO> detailsOnStatus = bookingService.getDetailsOnStatus(email, status);
        return new ResponseEntity(new StandardResponse(200,"Success",detailsOnStatus),HttpStatus.CREATED);
    }
}
