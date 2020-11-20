package com.easycar.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Driver {
    @Id
    String did;
    String driverName;
    String driverStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    List<BookingDetail> bookingDetails=new ArrayList<>();
}
