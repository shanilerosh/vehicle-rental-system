package com.easycar.spring.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<Booking> bookings=new ArrayList<>();
}
