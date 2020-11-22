package com.easycar.spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int detailId;
    private String status;
    private String waiverSlip;
    private Date rqrdDateTime;
    private Date dateOkBooking;
    private Date dateOfReturn;
    private String rqrdLocation;


    @ManyToOne()
    @JoinColumn(name="cr_Id",referencedColumnName = "reg",nullable = false)
    private Car car;

    public BookingDetail(String status, String waiverSlip, Date rqrdDateTime, Date dateOkBooking, Date dateOfReturn, String rqrdLocation) {
        this.status = status;
        this.waiverSlip = waiverSlip;
        this.rqrdDateTime = rqrdDateTime;
        this.dateOkBooking = dateOkBooking;
        this.dateOfReturn = dateOfReturn;
        this.rqrdLocation = rqrdLocation;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaiverSlip() {
        return waiverSlip;
    }

    public void setWaiverSlip(String waiverSlip) {
        this.waiverSlip = waiverSlip;
    }

    public Date getRqrdDateTime() {
        return rqrdDateTime;
    }

    public void setRqrdDateTime(Date rqrdDateTime) {
        this.rqrdDateTime = rqrdDateTime;
    }

    public Date getDateOkBooking() {
        return dateOkBooking;
    }

    public void setDateOkBooking(Date dateOkBooking) {
        this.dateOkBooking = dateOkBooking;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String getRqrdLocation() {
        return rqrdLocation;
    }

    public void setRqrdLocation(String rqrdLocation) {
        this.rqrdLocation = rqrdLocation;
    }

    @JsonBackReference
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @JsonBackReference
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JsonBackReference
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Return get_return() {
        return _return;
    }

    public void set_return(Return _return) {
        this._return = _return;
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
