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
    private String regNumber;
    private String brand;
    private String type;
    private int psngNumber;
    private double dailyRate;
    private int freeKmPerDay;
    private double monthlyRate;
    private int freeKmPerMonth;
    private double pricePerExtraKm;
    private String state;
}
