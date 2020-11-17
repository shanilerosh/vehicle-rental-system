package com.easycar.spring.repo;

import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car,Integer> {
}
