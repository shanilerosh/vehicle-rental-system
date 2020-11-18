package com.easycar.spring.controller;

import com.easycar.spring.dto.UserDTO;
import com.easycar.spring.entity.Booking;
import com.easycar.spring.entity.BookingDetail;
import com.easycar.spring.repo.BookingDetailsRepo;
import com.easycar.spring.repo.BookingRepo;
import com.easycar.spring.service.BookingService;
import com.easycar.spring.service.UserService;
import com.easycar.spring.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/checkuser")
    public ResponseEntity login(@RequestHeader("username") String username,@RequestHeader("password") String password) {
        System.out.println("here");
        UserDTO userDTO = userService.checkUser(username, password);
        userDTO.setPassword("");
        return new ResponseEntity(new StandardResponse(200,"Success",userDTO),HttpStatus.CREATED);
    }
}
