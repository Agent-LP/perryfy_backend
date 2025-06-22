package com.perryfyback.perryfy.models;

import lombok.Data;

@Data
public class ProductRequest {

    private String product_name;

    private long price;

    private String currency;

    private String description;

    private long stock;
}
