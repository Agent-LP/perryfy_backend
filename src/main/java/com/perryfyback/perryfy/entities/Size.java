package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer size_id;

    private String size;

    public Integer getSizeId() {
        return size_id;
    }

    public void setSizeId(Integer sizeId) {
        this.size_id = sizeId;
    }
} 