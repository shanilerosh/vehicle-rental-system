package com.easycar.spring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CustomerReturn")
public class Return {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;
    private int milage;
    private Date dteOfReturn;
    private double damages;

    @OneToOne()
    @JoinColumn(name = "bd_col", referencedColumnName = "detailId")
    @JsonManagedReference
    private BookingDetail bookingDetail;

    @OneToOne(mappedBy = "_return", cascade = CascadeType.ALL)
    private Payment payment;

    public Return(int milage, Date dteOfReturn, double damages, BookingDetail bookingDetail) {
        this.milage = milage;
        this.dteOfReturn = dteOfReturn;
        this.damages = damages;
        this.bookingDetail = bookingDetail;
    }
}
