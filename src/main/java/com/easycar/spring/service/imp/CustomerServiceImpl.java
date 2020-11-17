package com.easycar.spring.service.imp;

import com.easycar.spring.dto.CustomerDTO;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.User;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.CustomerService;
import com.easycar.spring.util.PasswordUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public void saveCustomer(Object[] saveCustomer)
    {
        MultipartFile file= (MultipartFile) saveCustomer[0];
        String email= (String) saveCustomer[1];
        String address= (String) saveCustomer[2];
        String name= (String) saveCustomer[3];
        String password= (String) saveCustomer[4];
        String filePath="";
        try {
            file.transferTo(new File("/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/src/main/images/customer"+file.getOriginalFilename()));
            filePath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/src/main/images/customer"+file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer customer = new Customer(email, name, address, filePath, password);
        customerRepo.save(customer);
    }
}
