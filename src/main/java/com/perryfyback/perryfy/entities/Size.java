package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  size_id;

    private String size;
} 