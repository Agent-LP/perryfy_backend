package com.perryfyback.perryfy.models.Printful;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMockupResponse {
    private List<String> mockupUrls;
} 