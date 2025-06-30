package com.perryfyback.perryfy.models.products;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintAreaResponse {
    private Integer printAreaId;
    private Integer width;
    private Integer height;
} 