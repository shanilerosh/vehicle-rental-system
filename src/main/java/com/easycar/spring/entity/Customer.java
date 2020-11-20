package com.easycar.spring.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Date;
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
    private Date dateOfReg;


    public Customer(String email, String name, String address, String document, String password, String salt, Date dateOfReg) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.document = document;
        this.password = password;
        this.salt = salt;
        this.dateOfReg = dateOfReg;
    }

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<BookingDetail> bookingDetails=new ArrayList<>();
}
