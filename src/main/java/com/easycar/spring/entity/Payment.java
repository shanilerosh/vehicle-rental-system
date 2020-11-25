package com.easycar.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;
    private Date dateOfPayment;
    private double amount;

    @OneToOne()
    @JoinColumn(name = "r_id", referencedColumnName = "rid")
    private Return _return;

    public Payment(Date dateOfPayment, double amount, Return _return) {
        this.dateOfPayment = dateOfPayment;
        this.amount = amount;
        this._return = _return;
    }
}
