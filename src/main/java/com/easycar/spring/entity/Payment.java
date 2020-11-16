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
public class Payment {
    @Id
    private String pid;
    private String dateOfPayment;
    private double amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "r_id",referencedColumnName = "rid",nullable = false)
    private Return _return;
}
