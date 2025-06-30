package com.perryfyback.perryfy.models.Printful;

import java.util.List;
import lombok.Data;

@Data
public class MockupGenerationRequest {
    private List<Integer> variants_id;
    private List<MockupFileRequest> files;
} 