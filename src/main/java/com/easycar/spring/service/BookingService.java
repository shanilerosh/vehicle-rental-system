package com.easycar.spring.service;

import com.easycar.spring.dto.BookingDetailDTO;
import com.easycar.spring.dto.BookingPendingDTO;
import com.easycar.spring.dto.DriverScheduleDTO;

import java.util.ArrayList;
import java.util.List;

public interface BookingService {
    void placeBooking(Object[] bookingDetailData);
    List<BookingDetailDTO> getDetailsOnStatus(String custEmail,String status);
    List<BookingPendingDTO> getPendingBooking(String status);
    List<DriverScheduleDTO> getDriverSchedule(String name, String pending,String from,String to);
    void finalizeBooking(String bid, String did);
}
