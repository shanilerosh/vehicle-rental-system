package com.easycar.spring.entity;

import com.easycar.spring.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;
    @Column(nullable = false)
    private String _date;
    @ManyToOne()
    @JoinColumn(name = "cemail", referencedColumnName = "email")
    private Customer customer;

    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<BookingDetail> bookingDetails=new ArrayList<>();

    public Booking(int bid, String _date) {
        this.bid = bid;
        this._date = _date;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
//    @JsonManagedReference
    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
