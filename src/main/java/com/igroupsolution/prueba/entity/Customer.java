package com.igroupsolution.prueba.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {

    private String customerId;
    private String created;
    private String email;
    private String name;
    private String payMode;
    private String creditCardType;
    private String last4CardDigits;
    private String externalId;
    private String status;
    private String registerDate;
}
