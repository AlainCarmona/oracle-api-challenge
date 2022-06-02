package com.oracle.apichallenge.entity;

import com.oracle.apichallenge.enums.EmployeeRoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class Employee {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private EmployeeRoleEnum role;
    private Address address;
}
