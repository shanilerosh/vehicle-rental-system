package com.easycar.spring.service;

import com.easycar.spring.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface BookingService {
    void placeBooking(Object[] bookingDetailData);

    List<BookingDetailDTO> getDetailsOnStatus(String custEmail, String status);

    List<BookingPendingDTO> getPendingBooking(String status);

    List<DriverScheduleDTO> getDriverSchedule(String name, String pending, String from, String to);

    void finalizeBooking(String bid, String did);

    void finalizeBookingWithoutDriver(String bid);

    void denyBooking(String bid, String denialMsg);

    List<CarScheduleDTO> getCarSchedule(String carId);

    BookingPendingDTO getBookingDetail(String id);

    List<BookingPendingDTO> getOpenBookingsForReturn(String selection, String value);

    Integer getBookingCountAsAt(LocalDate now);

    Integer getActiveBooking(String open);
}
