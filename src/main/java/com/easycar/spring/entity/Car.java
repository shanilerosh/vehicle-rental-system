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
    private String regNumber;
    private String brand;
    private String type;
    private int psngNumber;
    private double dailyRate;
    private int freeKmPerDay;
    private double monthlyRate;
    private int freeKmPerMonth;
    private double pricePerExtraKm;

    @OneToOne(mappedBy = "car",cascade = CascadeType.ALL)
    private BookingDetail bookingDetail;
}
