package com.perryfyback.perryfy.models.Printful;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private int area_width;
    private int area_height;
    private int width;
    private int height;
    private int top;
    private int left;
}
