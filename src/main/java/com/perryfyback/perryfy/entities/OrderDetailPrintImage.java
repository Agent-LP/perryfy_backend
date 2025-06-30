package com.perryfyback.perryfy.entities;

import com.perryfyback.perryfy.models.JPAModelTypes.OrderDetailPrintImageId;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orderdetail_printimages")
public class OrderDetailPrintImage {
    @EmbeddedId
    private OrderDetailPrintImageId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("colorId")
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @MapsId("sizeId")
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @MapsId("printImageId")
    @JoinColumn(name = "print_image_id")
    private PrintImage printImage;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false),
        @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false),
        @JoinColumn(name = "color_id", referencedColumnName = "color_id", insertable = false, updatable = false),
        @JoinColumn(name = "size_id", referencedColumnName = "size_id", insertable = false, updatable = false)
    })
    private OrderDetail orderDetail;
    
    @Column(insertable=false, updatable=false)
    private String position;
} 