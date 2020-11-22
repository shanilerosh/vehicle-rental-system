package com.easycar.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingPendingDTO {
    int bookingId;
    String customerName;
    String rqrdLocation;
    String bookingDate;
    String dateOfReturn;
    String driverId;
    String status;
}
