package com.easycar.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverScheduleDTO {
    private String driverId;
    private String driverName;
    private String fromDate;
    private String dateTo;
    private String location;
}
