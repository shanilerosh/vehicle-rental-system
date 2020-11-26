package com.easycar.spring.service;

import com.easycar.spring.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    void saveCustomer(Object[] saveCustomer);

    List<CustomerDTO> getAllWithName(String name, String criteria);

    List<CustomerDTO> searchAllCustomer();

    CustomerDTO searchSingleCustomer(String email);

    void updateCustomer(Object[] custArr);

    void updateContactOnly(String email, String contact);

    int getCustomerCount();
}
