package com.perryfyback.perryfy.models.products;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColorResponse {
    private Integer colorId;
    private String color;

    private String hexadecimal;

}
