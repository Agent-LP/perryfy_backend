package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer image_id;
    
    @Column(name = "image")
    private String image;

   
    public Integer getImageId() {
        return image_id;
    }

    public void setImageId(Integer imageId) {
        this.image_id = imageId;
    }
} 