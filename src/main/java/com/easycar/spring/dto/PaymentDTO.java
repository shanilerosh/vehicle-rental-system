package com.easycar.spring.dto;

import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    boolean isDly;
    double totalAmount;
    double driverAmt;
    double days;
    String customrName;
    String carId;
    String dteOfPayment;
}
