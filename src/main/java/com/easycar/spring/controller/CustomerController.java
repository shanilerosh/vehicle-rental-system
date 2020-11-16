package com.easycar.spring.controller;

import com.easycar.spring.dto.CustomerDTO;
import com.easycar.spring.service.CustomerService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService service;

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody CustomerDTO customerDTO) {
        service.saveCustomer(customerDTO);
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }
}
