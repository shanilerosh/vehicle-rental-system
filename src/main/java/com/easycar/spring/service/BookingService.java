package com.easycar.spring.service;

import com.easycar.spring.dto.BookingDetailDTO;

import java.util.List;

public interface BookingService {
    void placeBooking(Object[] bookingDetailData);
    List<BookingDetailDTO> getDetailsOnStatus(String custEmail,String status);
}
