package com.easycar.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Car(String name, String brand, String carType, String registrationNumb, Double mnthlyRate, Double dlyRate, Integer freeKmPerDay, Integer freeKmPerMonth, Double pricePerExtrakm, Integer nmberOfPssngers, String color, String carState, String interiorImge, String frntImg, String sideImg, String bckImg, String transmissionType, String fuelType, int milege) {
        this.name = name;
        this.brand = brand;
        this.carType = carType;
        this.registrationNumb = registrationNumb;
        this.mnthlyRate = mnthlyRate;
        this.dlyRate = dlyRate;
        this.freeKmPerDay = freeKmPerDay;
        this.freeKmPerMonth = freeKmPerMonth;
        this.pricePerExtrakm = pricePerExtrakm;
        this.nmberOfPssngers = nmberOfPssngers;
        this.color = color;
        this.carState = carState;
        this.interiorImge = interiorImge;
        this.frntImg = frntImg;
        this.sideImg = sideImg;
        this.bckImg = bckImg;
        this.transmissionType = transmissionType;
        this.fuelType = fuelType;
        this.milege = milege;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails=new ArrayList<>();




//    @OneToMany(mappedBy = "car")
//    private List<Maintanance> maintanances=new ArrayList<>();
}
