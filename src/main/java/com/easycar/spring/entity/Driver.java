package com.easycar.spring.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    @OneToOne(mappedBy = "driver")
    private  BookingDetail bookingDetail;
}
