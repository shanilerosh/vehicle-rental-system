package com.easycar.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
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


    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails=new ArrayList<>();

    public int getReg() {
        return reg;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCarType() {
        return carType;
    }

    public String getRegistrationNumb() {
        return registrationNumb;
    }

    public Double getMnthlyRate() {
        return mnthlyRate;
    }

    public Double getDlyRate() {
        return dlyRate;
    }

    public Integer getFreeKmPerDay() {
        return freeKmPerDay;
    }

    public Integer getFreeKmPerMonth() {
        return freeKmPerMonth;
    }

    public Double getPricePerExtrakm() {
        return pricePerExtrakm;
    }

    public Integer getNmberOfPssngers() {
        return nmberOfPssngers;
    }

    public String getColor() {
        return color;
    }

    public String getCarState() {
        return carState;
    }

    public String getInteriorImge() {
        return interiorImge;
    }

    public String getFrntImg() {
        return frntImg;
    }

    public String getSideImg() {
        return sideImg;
    }

    public String getBckImg() {
        return bckImg;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getMilege() {
        return milege;
    }

    @JsonManagedReference
    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    //    @OneToMany(mappedBy = "car")
//    private List<Maintanance> maintanances=new ArrayList<>();
}
