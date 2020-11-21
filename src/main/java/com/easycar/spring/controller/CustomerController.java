package com.easycar.spring.controller;

import com.easycar.spring.dto.CustomerDTO;
import com.easycar.spring.service.CustomerService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping(path = "/savecustomer")
    public ResponseEntity addCustomer(@RequestParam("doc") MultipartFile file,@RequestParam("email") String email,
                              @RequestParam("address") String address,@RequestParam("name") String name,@RequestParam("password") String password) {
        Object[] custArr={file,email,address,name,password};
        service.saveCustomer(custArr);
        return new ResponseEntity(new StandardResponse(200,"Success",null),HttpStatus.CREATED);
    }


    @GetMapping(path = "/searchcustomer/{val}/{criteria}")
    public ResponseEntity searchCustomer(@PathVariable String val,@PathVariable String criteria){
        System.out.println("insde");
        List<CustomerDTO> allWithName = service.getAllWithName(val,criteria);
        return new ResponseEntity(new StandardResponse(200,"Success",allWithName),HttpStatus.CREATED);
    }

    @GetMapping(path = "/searchallcustomer")
    public ResponseEntity searchCustomer(){
        List<CustomerDTO> allCustomers = service.searchAllCustomer();
        return new ResponseEntity(new StandardResponse(200,"Success",allCustomers),HttpStatus.CREATED);
    }


    @GetMapping(path = "/searchonecustomer/{email}")
    public ResponseEntity searchSingleCustomer(@PathVariable String email){
        System.out.println(email+" called");
        CustomerDTO customerDTO = service.searchSingleCustomer(email);
        return new ResponseEntity(new StandardResponse(200,"Success",customerDTO),HttpStatus.CREATED);
    }
}
