package com.oracle.apichallenge.dto;

import com.oracle.apichallenge.enums.EmployeeRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateEmployeeRequestDto {
    private String firstName;
    private String lastName;
    private EmployeeRoleEnum role;
    private String street;
    private String county;
    private String city;
    private String state;
    private int postalCode;
    private String country;
    private int phoneCountryCode;
    private long phoneNumber;
}
