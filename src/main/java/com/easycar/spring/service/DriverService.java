package com.easycar.spring.service;

import com.easycar.spring.dto.DriverDTO;
import com.easycar.spring.dto.DriverScheduleDTO;

import java.util.ArrayList;

public interface DriverService {
    void addDriver(DriverDTO driverDTO);

    ArrayList<DriverDTO> findDriverNamesExcept(String except);

    ArrayList<Integer> countAvlAndOccupied();
}
