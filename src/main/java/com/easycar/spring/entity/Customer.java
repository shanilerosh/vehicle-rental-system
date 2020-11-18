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
    private String email;
    private String name;
    private String address;
    private String document;
    private String password;
    private String salt;


    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<Booking> bookings=new ArrayList<>();

    public Customer(String email, String name, String address, String document, String password,String salt) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.document = document;
        this.password = password;
        this.salt=salt;
    }
}
