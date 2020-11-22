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
public class PaymentDetailDTO {
    private int detail;
    private String dateTime;
    private String status;
    private Car car;
    private Driver driver;
    private Customer customer;
}
