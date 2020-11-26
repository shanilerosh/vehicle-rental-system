package com.easycar.spring.repo;

import com.easycar.spring.entity.BookingDetail;
import com.easycar.spring.entity.Car;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface BookingDetailsRepo extends JpaRepository<BookingDetail, Integer> {
    List<BookingDetail> getAllByCustomerAndStatus(Customer customer, String status);

    List<BookingDetail> getAllByStatus(String status);

    List<BookingDetail> findAllByRqrdDateTimeGreaterThanEqualAndDateOfReturn(Date from, Date to);

    List<BookingDetail> findAllByCar(Car car);

    List<BookingDetail> findAllByStatusAndDetailIdStartingWith(String status, Integer val);

    List<BookingDetail> findAllByCustomerAndStatus(Customer customer, String status);

    Integer countByDateOkBooking(Date dte);

    Integer countByStatus(String status);
}
