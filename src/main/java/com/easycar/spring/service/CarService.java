package com.easycar.spring.service;

import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.dto.CustomerDTO;

import java.util.ArrayList;
import java.util.List;

public interface CarService {
    void addCar(Object[] dataSet);

    void updateCar(Object[] dataSet);

    List<CarDTO> getAllCars();

    CarDTO getCarByReg(String reg);

    CarDTO getCarByPk(String cid);

    ArrayList<CarDTO> getCarByState(String state);

    void updateState(String crId, String val);

    List<CarDTO> getAllCarsWhenTyping(String selected, String custInput);

    List<CarDTO> searchAllTypeCarsWhenTyping(String selected, String custInput);

    ArrayList<Integer> getAvailbleAndReserved();

    Integer findCarsForMaintainance();
}
