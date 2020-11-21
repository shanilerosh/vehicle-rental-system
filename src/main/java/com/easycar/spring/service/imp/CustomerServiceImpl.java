package com.easycar.spring.service.imp;

import com.easycar.spring.dto.CarDTO;
import com.easycar.spring.dto.CustomerDTO;
import com.easycar.spring.entity.Customer;
import com.easycar.spring.entity.User;
import com.easycar.spring.repo.CustomerRepo;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.CustomerService;
import com.easycar.spring.util.PasswordUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
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
            file.transferTo(new File("/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/customer/"+file.getOriginalFilename()));
            filePath="/home/shanil/Work Related Practice_Final Tech Wise/Java/CarRentalSystem_EasyCar/Front_End/assets/images/customer/"+file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //decrypting pass
        String salt = PasswordUtils.getSalt(8);
        String generateSecurePassword = PasswordUtils.generateSecurePassword(password, salt);
        Customer customer = new Customer(email, name, address, filePath, generateSecurePassword,salt, Date.valueOf(LocalDate.now()));
        System.out.println(salt+" pass:"+password);
        customerRepo.save(customer);
        //Adding as user
        userRepo.save(new User("customer",customer.getEmail(),customer.getEmail(),customer.getPassword(),customer.getSalt()));;
    }

    @Override
    public List<CustomerDTO> getAllWithName(String val,String criteria) {
        if(criteria.equals("Name")) {
            List<Customer> allByNameLike = customerRepo.findAllByNameStartingWith(val);
            return mapper.map(allByNameLike,new TypeToken<List<CustomerDTO>>(){}.getType());
        }else if(criteria.equals("Email")) {
            List<Customer> allByNameLike = customerRepo.findAllByEmailStartingWith(val);
            return mapper.map(allByNameLike,new TypeToken<List<CustomerDTO>>(){}.getType());
        }else if(criteria.equals("Address")) {
            List<Customer> allByNameLike = customerRepo.findAllByAddressStartingWith(val);
            return mapper.map(allByNameLike,new TypeToken<List<CustomerDTO>>(){}.getType());
        }else {
            List<Customer> all = customerRepo.findAll();
            return mapper.map(all,new TypeToken<List<CustomerDTO>>(){}.getType());
        }
    }

    @Override
    public List<CustomerDTO> searchAllCustomer() {
        List<Customer> all = customerRepo.findAll();
        return mapper.map(all,new TypeToken<List<CustomerDTO>>(){}.getType());

    }

    @Override
    public CustomerDTO searchSingleCustomer(String email) {
        Optional<Customer> byId = customerRepo.findById(email);
        if(byId.isPresent()){
            return mapper.map(byId.get(),CustomerDTO.class);
        }
        return null;
    }
}
