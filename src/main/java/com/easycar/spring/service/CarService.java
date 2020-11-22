package com.easycar.spring.service;

import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.dto.CustomerDTO;

import java.util.ArrayList;
import java.util.List;

public interface CarService {
    void addCar(Object[] dataSet);
    List<CarDTO> getAllCars();
    CarDTO getCarByReg(String reg);
    CarDTO getCarByPk(String cid);

    ArrayList<CarDTO> getCarByState(String state);

    void updateState(String crId, String val);
}
