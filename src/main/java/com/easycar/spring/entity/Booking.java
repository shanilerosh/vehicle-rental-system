package com.easycar.spring.entity;

import com.easycar.spring.dto.CustomerDTO;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int bid;
    @Column(nullable = false)
    private String _date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "custId", referencedColumnName = "email",nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
    List<BookingDetail> bookingDetails=new ArrayList<>();

    public Booking(String date, Customer customer) {
        this._date = date;
        this.customer = customer;
    }
}
