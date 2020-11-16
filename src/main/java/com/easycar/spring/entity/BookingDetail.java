package com.easycar.spring.entity;

import javax.persistence.*;

@Entity
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private double detailId;
    private String basis;
    private String waiverSlip;
    private int amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bk_Id",referencedColumnName = "bid",nullable = false)
    private Booking booking;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cr_Id",referencedColumnName = "regNumber",nullable = false)
    private Car car;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="d_Id",referencedColumnName = "did")
    private Driver driver;


    @OneToOne(mappedBy = "bookingDetail",cascade = CascadeType.ALL)
    private Return _return;
}
