package com.easycar.spring.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cid;
    private String email;
    private String name;
    private String address;
    private String document;
    private String password;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<Booking> bookings=new ArrayList<>();
}
