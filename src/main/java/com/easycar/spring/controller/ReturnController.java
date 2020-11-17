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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/return")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @Autowired
    private ReturnRepo repo;

    @PostMapping
    public ResponseEntity addReturn(@RequestBody Return returnDTO) {
        repo.save(returnDTO);
        return new ResponseEntity(new StandardResponse(200,"Success",null), HttpStatus.CREATED);
    }
}
