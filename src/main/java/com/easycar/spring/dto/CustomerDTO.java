package com.easycar.spring.dto;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String email;
    private String name;
    private String address;
    private String contact;
    private String document;
    private String password;
    private String salt;
    private Date dateOfReg;
}
