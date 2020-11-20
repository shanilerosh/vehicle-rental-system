package com.easycar.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int detailId;
    private String status;
    private String waiverSlip;
    private Timestamp rqrdDateTime;
    private Timestamp dateOkBooking;
    private String rqrdLocation;

    @ManyToOne()
    @JoinColumn(name="cr_Id",referencedColumnName = "reg",nullable = false)
    private Car car;

    public BookingDetail(String status, String waiverSlip, Timestamp rqrdDateTime, Timestamp dateOkBooking, String rqrdLocation) {
        this.status = status;
        this.waiverSlip = waiverSlip;
        this.rqrdDateTime = rqrdDateTime;
        this.dateOkBooking = dateOkBooking;
        this.rqrdLocation = rqrdLocation;
    }

    @ManyToOne
    @JoinColumn(name = "cust_Id",referencedColumnName = "email",nullable = false)
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="d_Id",referencedColumnName = "did")
    private Driver driver;


    @OneToOne(mappedBy = "bookingDetail",cascade = CascadeType.ALL)
    private Return _return;
}
