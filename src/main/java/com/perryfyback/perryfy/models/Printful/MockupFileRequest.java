package com.perryfyback.perryfy.models.Printful;

import lombok.Data;

@Data
public class MockupFileRequest {
    private String placement;
    private String image_url;
    private PositionRequest position;
} 