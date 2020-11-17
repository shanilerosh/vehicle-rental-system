package com.easycar.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int detailId;
    private String basis;
    private String waiverSlip;
    private int amount;

    @ManyToOne()
    @JoinColumn(name = "bk_Id",referencedColumnName = "bid")
    private Booking booking;

    @ManyToOne()
    @JoinColumn(name="cr_Id",referencedColumnName = "reg")
    private Car car;


    @OneToOne()
    @JoinColumn(name="d_Id",referencedColumnName = "did")
    private Driver driver;


    @OneToOne(mappedBy = "bookingDetail",cascade = CascadeType.ALL)
    private Return _return;
}
