package com.easycar.spring.service.imp;

import com.easycar.spring.dto.CustomerDTO;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.User;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepo repo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public void saveCustomer(CustomerDTO customerDTO)
    {
        repo.save(mapper.map(customerDTO, Customer.class));
        User user=new User();
        user.setRole("customer");
        user.setPassword(customerDTO.getPassword());
        user.setUserName(customerDTO.getEmail());
        userRepo.save(user);
    }
}
