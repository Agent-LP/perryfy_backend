package com.perryfyback.perryfy.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("product_Id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("color_id")
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @MapsId("size_id")
    @JoinColumn(name = "size_id")
    private Size size;

    private Integer quantity;
    private BigDecimal price;

    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private Set<OrderDetailPrintImage> printImages;
}

@Embeddable
@Data
class OrderDetailId {
    private Integer  order_id;
    private Integer  product_Id;
    private Integer  color_id;
    private Integer  size_id;
} 