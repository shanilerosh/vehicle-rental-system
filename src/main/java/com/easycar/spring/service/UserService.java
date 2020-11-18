package com.easycar.spring.service;

import com.easycar.spring.dto.UserDTO;

public interface UserService {
    void addUser(UserDTO userDTO);
    UserDTO checkUser(String username,String password);
}
