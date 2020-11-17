package com.easycar.spring.service.imp;

import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.entity.Car;
import com.easycar.spring.repo.CarRepo;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepo carRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public void addCar(CarDTO carDTO) {
        carRepo.save(mapper.map(carDTO, Car.class));
    }
}
