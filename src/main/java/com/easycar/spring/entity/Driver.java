package com.easycar.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Driver {
    @Id
    String did;
    String driverName;
    String driverStatus;


    @JsonIgnore
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<BookingDetail> bookingDetails=new ArrayList<>();

    public String getDid() {
        return did;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    @JsonIgnore
    @JsonManagedReference
    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }
}
