package com.easycar.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails=new ArrayList<>();

//    @OneToMany(mappedBy = "car")
//    private List<Maintanance> maintanances=new ArrayList<>();
}
