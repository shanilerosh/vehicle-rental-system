package com.easycar.spring.repo;

import com.easycar.spring.entity.BookingDetail;
import com.easycar.spring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingDetailsRepo extends JpaRepository<BookingDetail,Integer> {
    public List<BookingDetail> getAllByCustomerAndStatus(Customer customer, String status);
}
