package com.perryfyback.perryfy.services.printful;

import com.perryfyback.perryfy.models.Printful.CreateMockupResponse;
import com.perryfyback.perryfy.models.Printful.GetMockupResponse;
import com.perryfyback.perryfy.models.Printful.MockupGenerationRequest;

public interface PrintfulService {
    CreateMockupResponse createMockup(MockupGenerationRequest request);
    GetMockupResponse getMockups(String taskKey);
}
