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
    String remarks;

    public BookingPendingDTO(int bookingId, String customerName, String rqrdLocation, String bookingDate, String dateOfReturn, String driverId, String status) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.rqrdLocation = rqrdLocation;
        this.bookingDate = bookingDate;
        this.dateOfReturn = dateOfReturn;
        this.driverId = driverId;
        this.status = status;
    }
}
