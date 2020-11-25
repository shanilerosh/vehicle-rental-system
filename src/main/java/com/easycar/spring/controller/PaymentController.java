package com.easycar.spring.controller;

import com.easycar.spring.dto.BookingDetailDTO;
import com.easycar.spring.dto.PaymentDTO;
import com.easycar.spring.service.PaymentService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;


    @PostMapping
    public ResponseEntity recordPayment(@RequestParam("rid") String rid) {
        System.out.println(rid);
        PaymentDTO paymentDTO = paymentService.recordPayment(rid);
        return new ResponseEntity(new StandardResponse(200, "Success", paymentDTO), HttpStatus.CREATED);
    }

}
