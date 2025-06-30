package com.perryfyback.perryfy.models.Printful;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MockupFile {
    private String placement;
    private String image_url;
    private Position position;
}
