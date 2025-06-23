package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "print_images")
public class PrintImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  print_image_id;

    private String print_image;

    @OneToMany(mappedBy = "printImage", cascade = CascadeType.ALL)
    private Set<OrderDetailPrintImage> orderDetailPrintImages;
} 