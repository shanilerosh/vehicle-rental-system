package com.easycar.spring.repo;

import com.easycar.spring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,String> {
    List<Customer> findAllByNameStartingWith(String name);
    List<Customer> findAllByEmailStartingWith(String name);
    List<Customer> findAllByAddressStartingWith(String name);

}
