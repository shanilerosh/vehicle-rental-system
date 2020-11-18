package com.easycar.spring.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String role;
    private String partyId;
    private String userName;
    private String password;
    private String salt;

    public UserDTO(String role, String partyId, String userName, String password, String salt) {
        this.role = role;
        this.partyId = partyId;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }
}
