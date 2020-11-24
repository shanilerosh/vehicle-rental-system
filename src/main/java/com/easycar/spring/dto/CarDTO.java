package com.easycar.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarDTO {
    private int reg;
    private String name;
    private String brand;
    private String carType;
    private String registrationNumb;
    private Double mnthlyRate;
    private Double dlyRate;
    private Integer freeKmPerDay;
    private Integer freeKmPerMonth;
    private Double pricePerExtrakm;
    private Integer nmberOfPssngers;
    private String color;
    private String carState;
    private String interiorImge;
    private String frntImg;
    private String sideImg;
    private String bckImg;
    private String transmissionType;
    private String fuelType;
    private int milege;
    private int deposit;


}
