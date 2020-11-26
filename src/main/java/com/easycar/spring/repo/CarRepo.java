package com.easycar.spring.repo;

import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CarRepo extends JpaRepository<Car, Integer> {
     Car findCarsByRegistrationNumb(String reg);

     Car findCarByReg(int reg);

     ArrayList<Car> findAllByCarState(String state);

     ArrayList<Car> findAllByCarStateAndColorStartingWith(String state, String color);

     ArrayList<Car> findAllByCarStateAndRegistrationNumbStartingWith(String state, String color);

     ArrayList<Car> findAllByCarStateAndCarTypeStartingWith(String state, String color);

     ArrayList<Car> findAllByCarStateAndNmberOfPssngersStartingWith(String state, Integer pssnger);

     ArrayList<Car> findAllByCarStateAndFuelTypeStartingWith(String state, String fuelType);

     ArrayList<Car> findAllByCarStateAndTransmissionTypeStartingWith(String state, String trsntype);

     ArrayList<Car> findAllByBrandStartingWith(String brand);

     ArrayList<Car> findAllByCarTypeStartingWith(String type);

     ArrayList<Car> findAllByFuelTypeStartingWith(String fuel);

     ArrayList<Car> findAllByTransmissionTypeStartingWith(String brand);

     Integer countByCarState(String state);

     Integer countAllByMilegeGreaterThan(int milage);
}
