package com.easycar.spring.service.imp;

import com.easycar.spring.dto.DriverDTO;
import com.easycar.spring.entity.Booking;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.Driver;
import com.easycar.spring.repo.BookingRepo;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.DriverRepo;
import com.easycar.spring.service.BookingService;
import com.easycar.spring.service.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {


    @Autowired
    ModelMapper mapper;

    @Autowired
    DriverRepo driverRepo;

    @Override
    public void addDriver(DriverDTO driverDTO) {
        driverRepo.save(mapper.map(driverDTO, Driver.class));
    }
}
