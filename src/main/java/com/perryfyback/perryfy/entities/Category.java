package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer category_id;

    private String category;

    public Integer getCategoryId() {
        return category_id;
    }

    public void setCategoryId(Integer categoryId) {
        this.category_id = categoryId;
    }
} 