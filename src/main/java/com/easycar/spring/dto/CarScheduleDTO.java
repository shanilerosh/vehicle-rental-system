package com.easycar.spring.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarScheduleDTO {
    String rqrdDate;
    String retrunDate;
    String driver;
}
