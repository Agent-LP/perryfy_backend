package com.perryfyback.perryfy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perryfyback.perryfy.models.Printful.CreateMockupResponse;
import com.perryfyback.perryfy.models.Printful.GetMockupResponse;
import com.perryfyback.perryfy.models.Printful.MockupGenerationRequest;
import com.perryfyback.perryfy.services.printful.PrintfulService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/printful")
@RequiredArgsConstructor
public class PrintfulController {

    private final PrintfulService printfulService;

    @PostMapping("/mockup-generator")
    
    public ResponseEntity<CreateMockupResponse> createMockup(@RequestBody MockupGenerationRequest request) {
        CreateMockupResponse response = printfulService.createMockup(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mockup-generator/task/{taskKey}")
    
    public ResponseEntity<GetMockupResponse> getMockupResult(@PathVariable String taskKey) {
        GetMockupResponse response = printfulService.getMockups(taskKey);
        return ResponseEntity.ok(response);
    }
} 