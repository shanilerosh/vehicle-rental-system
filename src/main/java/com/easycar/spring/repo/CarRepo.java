package com.easycar.spring.repo;

import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CarRepo extends JpaRepository<Car,Integer> {
     Car findCarsByRegistrationNumb(String reg);
     Car findCarByReg(int reg);
     ArrayList<Car> findAllByCarState(String state);

}
