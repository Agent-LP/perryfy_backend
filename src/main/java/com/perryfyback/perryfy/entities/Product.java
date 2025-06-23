package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  product_id;

    @Column(name = "product_name")
    private String product_name;
    
    private BigDecimal price;
    private String currency;
    private Integer stock;
    private String description;

    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer productId) {
        this.product_id = productId;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @ManyToMany
    @JoinTable(
        name = "product_color",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private Set<Color> colors;

    @ManyToMany
    @JoinTable(
        name = "product_size",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private Set<Size> sizes;

    @ManyToMany
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToMany
    @JoinTable(
        name = "product_image",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Image> images;

    @ManyToMany
    @JoinTable(
        name = "product_print_area",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "print_area_id")
    )
    private Set<PrintArea> printAreas;
} 