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
    public List<BookingDetail> getAllByCustomerAndStatus(Customer customer, String status);

    public List<BookingDetail> getAllByStatus(String status);

    public List<BookingDetail> findAllByRqrdDateTimeGreaterThanEqualAndDateOfReturn(Date from, Date to);

    public List<BookingDetail> findAllByCar(Car car);

    public List<BookingDetail> findAllByStatusAndDetailIdStartingWith(String status, Integer val);

    public List<BookingDetail> findAllByCustomerAndStatus(Customer customer, String status);
}
