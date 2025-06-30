package com.perryfyback.perryfy.models.Printful;

import java.util.List;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    private List<Integer> variant_ids ;
    private String format ;
    private List<MockupFile> files;
}
