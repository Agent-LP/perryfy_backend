package com.perryfyback.perryfy.models.JPAModelTypes;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class OrderDetailPrintImageId {
    private Integer orderId;
    private Integer productId;
    private Integer colorId;
    private Integer sizeId;
    private Integer printImageId;
    private String position;
} 