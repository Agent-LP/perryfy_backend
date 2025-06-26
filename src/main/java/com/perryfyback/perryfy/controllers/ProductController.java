package com.perryfyback.perryfy.controllers;

import com.perryfyback.perryfy.models.products.ProductRequest;
import com.perryfyback.perryfy.models.products.ProductResponse;
import com.perryfyback.perryfy.services.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('MERCHANDISER')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MERCHANDISER')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MERCHANDISER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        return productService.deleteProduct(id);
    }
}
