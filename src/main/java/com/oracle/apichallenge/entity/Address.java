package com.oracle.apichallenge.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Address {
    private String street;
    private String county;
    private String city;
    private String state;
    private int postalCode;
    private String country;
    private PhoneDetails phoneDetails;
}
