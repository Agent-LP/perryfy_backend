package com.perryfyback.perryfy.models.products;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private long productId;
    
    private String product_name;

    private long price;

    private String currency;

    private String description;

    private long stock;

    private Integer variantId;

    private Integer printfulProductId;


    private List<String> imageUrls;
    private List<String> categories;
    private List<ColorResponse> colors;
    private List<String> sizes;
    private List<PrintAreaResponse> printAreas;


    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }


    public Integer getPrintfulProductId() {
        return printfulProductId;
    }

    public void setPrintfulProductId(Integer printfulProductId) {
        this.printfulProductId = printfulProductId;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }
}
