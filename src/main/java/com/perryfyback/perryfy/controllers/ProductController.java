package com.perryfyback.perryfy.controllers;

import com.perryfyback.perryfy.models.ProductRequest;
import com.perryfyback.perryfy.models.ProducResponse;
import com.perryfyback.perryfy.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProducResponse>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProducResponse> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ProducResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProducResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }
}
