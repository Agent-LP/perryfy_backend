package com.perryfyback.perryfy.models.users;

import lombok.Data;

@Data
public class UserRequest {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String defaultAddress;
    private String paymentMethodToken;
} 