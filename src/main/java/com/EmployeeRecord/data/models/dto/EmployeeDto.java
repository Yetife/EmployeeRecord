package com.EmployeeRecord.data.models.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String homeAddress;
    private String designation;
}
