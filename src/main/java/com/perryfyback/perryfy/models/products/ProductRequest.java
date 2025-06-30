package com.perryfyback.perryfy.models.products;

import lombok.Data;
import java.util.List;

@Data
public class ProductRequest {
    private String product_name;
    private long price;
    private String currency;
    private String description;
    private long stock;
    private Integer variantId;
    private Integer printfulProductId;

    
    // Related entities
    private List<Integer> colorIds;
    private List<Integer> sizeIds;
    private List<Integer> categoryIds;
    private List<String> imageUrls;
    private List<PrintAreaRequest> printAreas;
} 