package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer color_id;

    private String color;

    public Integer getColorId() {
        return color_id;
    }

    public void setColorId(Integer colorId) {
        this.color_id = colorId;
    }
} 