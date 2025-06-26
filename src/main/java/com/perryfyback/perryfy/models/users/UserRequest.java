package com.perryfyback.perryfy.models.users;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String defaultAddress;
    private String paymentMethodToken;
} 