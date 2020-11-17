package com.easycar.spring.dto;

import com.easycar.spring.entity.BookingDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingDTO {
    private int bid;
    private String date;
    private CustomerDTO customerDTO;

    public BookingDTO(String date, CustomerDTO customerDTO) {
        this.date = date;
        this.customerDTO = customerDTO;
    }
}
