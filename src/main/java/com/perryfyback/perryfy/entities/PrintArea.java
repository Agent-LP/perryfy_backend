package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "print_areas")
public class PrintArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer print_area_id;

    private Integer width;
    private Integer height;

    public Integer getPrintAreaId() {
        return print_area_id;
    }

    public void setPrintAreaId(Integer printAreaId) {
        this.print_area_id = printAreaId;
    }
} 