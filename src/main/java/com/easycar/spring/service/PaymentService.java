package com.easycar.spring.service;

import com.easycar.spring.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO recordPayment(String rid);
}
