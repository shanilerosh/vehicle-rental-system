package com.easycar.spring.entity;

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
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String role;
    private String partyId;
    private String userName;
    private String password;
    private String salt;

    public User(String role, String partyId, String userName, String password, String salt) {
        this.role = role;
        this.partyId = partyId;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
    }
}
