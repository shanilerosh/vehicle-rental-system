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


    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
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

    @JsonManagedReference
    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }
}
