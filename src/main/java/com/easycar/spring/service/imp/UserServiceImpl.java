package com.easycar.spring.service.imp;

import com.easycar.spring.dto.UserDTO;
import com.easycar.spring.entity.User;
import com.easycar.spring.repo.UserRepo;
import com.easycar.spring.service.UserService;
import com.easycar.spring.util.PasswordUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepo repo;

    @Override
    public void addUser(UserDTO userDTO) {
        repo.save(mapper.map(userDTO, User.class));
    }

    @Override
    public UserDTO checkUser(String username, String password) {
        User user = repo.findByUserName(username);
        if(user==null) {
            throw new RuntimeException(username+" Doesn't Exist. Please try again lated");
        }
        boolean res = PasswordUtils.verifyUserPassword(password, user.getPassword(), user.getSalt());
        if(!res){
            throw new RuntimeException("Your password is Incorrect.Try again Later");
        }
        return mapper.map(user,UserDTO.class);
    }
}
