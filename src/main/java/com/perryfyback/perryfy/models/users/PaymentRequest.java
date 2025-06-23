package com.perryfyback.perryfy.models.users;

import lombok.Data;

@Data
public class PaymentRequest {
    private Integer userId;
    private String stripeProductId;
} 