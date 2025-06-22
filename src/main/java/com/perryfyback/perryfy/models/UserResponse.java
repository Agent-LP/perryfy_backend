package com.perryfyback.perryfy.models;

import lombok.Data;

@Data
public class UserResponse {
    private Integer user_id;
    private String name;
    private String lastname;
    private String email;
    private String defaultAddress;
    private String paymentMethodToken;
} 