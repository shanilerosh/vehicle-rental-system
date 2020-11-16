package com.easycar.spring.service.imp;

import com.easycar.spring.dto.BookingDTO;
import com.easycar.spring.dto.CustomerDTO;
import com.easycar.spring.entity.Booking;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.User;
import com.easycar.spring.repo.BookingRepo;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.BookingService;
import com.easycar.spring.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {


    @Autowired
    ModelMapper mapper;

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public void addBooking(String date,String email){
        Customer customer = customerRepo.getOne(email);
        bookingRepo.save(new Booking(date,customer));
    }
}
