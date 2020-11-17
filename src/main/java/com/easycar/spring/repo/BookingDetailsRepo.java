package com.easycar.spring.repo;

import com.easycar.spring.entity.Booking;
import com.easycar.spring.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailsRepo extends JpaRepository<BookingDetail,Integer> {
}
