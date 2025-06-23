package com.perryfyback.perryfy.models.users;

import lombok.Data;

@Data
public class PaymentResponse {
    private String paymentIntentId;
    private String status;
    private Long amount;
    private String currency;
} 