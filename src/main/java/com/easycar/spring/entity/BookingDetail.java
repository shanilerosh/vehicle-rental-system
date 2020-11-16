package com.easycar.spring.entity;

import javax.persistence.*;

@Entity
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long detailId;
    private String basis;
    private String waiverSlip;
    private int amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bk_Id",referencedColumnName = "bid",nullable = false)
    private Booking booking;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cr_Id",referencedColumnName = "regNumber",nullable = false)
    private Car car;
}
