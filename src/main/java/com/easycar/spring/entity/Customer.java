package com.easycar.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    private String contact;
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

    @JsonIgnore
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<BookingDetail> bookingDetails=new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDocument() {
        return document;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Date getDateOfReg() {
        return dateOfReg;
    }

    @JsonManagedReference
    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }
}
