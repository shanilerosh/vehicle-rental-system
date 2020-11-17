package com.easycar.spring.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Maintanance {
    @Id
    public String mid;
    public String dateOfMain;
    public String receivingdate;


//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "cr_id",referencedColumnName = "regNumber",nullable = false)
//    private Car car;
}
