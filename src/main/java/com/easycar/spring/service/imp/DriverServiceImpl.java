package com.easycar.spring.service.imp;

import com.easycar.spring.dto.DriverDTO;
import com.easycar.spring.entity.Driver;
import com.easycar.spring.repo.DriverRepo;
import com.easycar.spring.service.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ArrayList<DriverDTO>findDriverNamesExcept(String except) {
        List<Driver> all = driverRepo.findAll();
        ArrayList<DriverDTO> driverDTOS = new ArrayList<>();
        for (Driver driver : all) {
            System.out.println("Isde loop");
            if(!driver.getDid().equals(except)){
                System.out.println("Insd if block");
                driverDTOS.add(new DriverDTO(driver.getDid(),driver.getDriverName(),driver.getDriverStatus()));
            }
        }
        return driverDTOS;
    }
}
