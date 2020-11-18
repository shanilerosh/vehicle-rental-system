package com.easycar.spring.service.imp;

import com.easycar.spring.dto.CustomerDTO;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.User;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.CustomerService;
import com.easycar.spring.util.PasswordUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepo userRepo;

    @Override
    public void saveCustomer(Object[] saveCustomer)
    {

        String email= (String) saveCustomer[1];
        Optional<Customer> exist = customerRepo.findById(email);
        if(exist.isPresent()){
            throw new RuntimeException("Error "+email+" already exist.Please login if you exist");
        }
        customerRepo.findAll();
        MultipartFile file= (MultipartFile) saveCustomer[0];
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
        //decrypting pass
        String salt = PasswordUtils.getSalt(8);
        String generateSecurePassword = PasswordUtils.generateSecurePassword(password, salt);
        Customer customer = new Customer(email, name, address, filePath, generateSecurePassword,salt);
        System.out.println(salt+" pass:"+password);
        customerRepo.save(customer);
        //Adding as user
        userRepo.save(new User("customer",customer.getEmail(),customer.getEmail(),customer.getPassword(),customer.getSalt()));;
    }
}
