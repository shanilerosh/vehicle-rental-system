package com.easycar.spring.controller;

import com.easycar.spring.dto.BookingDetailDTO;
import com.easycar.spring.dto.BookingDriverDTO;
import com.easycar.spring.dto.BookingPendingDTO;
import com.easycar.spring.dto.DriverScheduleDTO;
import com.easycar.spring.entity.BookingDetail;
import com.easycar.spring.entity.Driver;
import com.easycar.spring.repo.BookingDetailsRepo;
import com.easycar.spring.repo.DriverRepo;
import com.easycar.spring.service.BookingService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    BookingDetailsRepo repo;

    @Autowired
    DriverRepo driverRepo;

    @PostMapping(path = "/bookingdetail")
    public ResponseEntity addBooking(@RequestParam("bcktLocation") String bcktLocation, @RequestParam("bcktPckUp") String bcktPckUp,
                                     @RequestParam("driver") String driver, @RequestParam("vehicleId") String vehicleId,
                                     @RequestParam("customer") String customer,@RequestParam("bcktDoc") MultipartFile bcktDoc,
                                     @RequestParam("returnDate") String returnDate){
        System.out.println(bcktPckUp);
        System.out.println(driver);
        System.out.println(vehicleId);
        Object[] bookingDetailData={
            bcktLocation,
            bcktPckUp,
            driver,
            vehicleId,
            bcktDoc,
            customer,
            returnDate
        };

        bookingService.placeBooking(bookingDetailData);
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }


    @GetMapping(path = "/getonstatus/{email}/{status}")
    public ResponseEntity  getOnStatus(@PathVariable String email,@PathVariable String status) {
        List<BookingDetailDTO> detailsOnStatus = bookingService.getDetailsOnStatus(email, status);
        return new ResponseEntity(new StandardResponse(200,"Success",detailsOnStatus),HttpStatus.CREATED);
    }

    @GetMapping(path = "/getpending")
    public ResponseEntity  getPending() {
        List<BookingPendingDTO> pendingDTo=bookingService.getPendingBooking("pending");
        System.out.println("here");
        return new ResponseEntity(new StandardResponse(200,"Success",pendingDTo),HttpStatus.CREATED);
    }

    @GetMapping(path = "/getdriverchedule/{did}/{from}/{to}")
    public ResponseEntity  getDriverSchedule(@PathVariable String did,@PathVariable String from,@PathVariable String to) {
        List<DriverScheduleDTO> driverSchedule=bookingService.getDriverSchedule(did,"pending",from,to);
        return new ResponseEntity(new StandardResponse(200,"Success",driverSchedule),HttpStatus.CREATED);
    }


    @PostMapping(path = "/finalizebooking")
    public ResponseEntity  finalizeBooking(@RequestBody BookingDriverDTO dto) {
        bookingService.finalizeBooking(dto.getBid(),dto.getDid());
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }

    @PostMapping(path = "/testbooking")
    public ResponseEntity  test(@RequestBody BookingDriverDTO dto) {
        System.out.println("Booking id "+dto.getBid());
        Optional<BookingDetail> byId = repo.findById(Integer.parseInt(dto.getBid()));
        return new ResponseEntity(new StandardResponse(200,"Success",byId.get()),HttpStatus.CREATED);
    }
}
