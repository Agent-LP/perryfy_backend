package com.perryfyback.perryfy.services;

import com.perryfyback.perryfy.models.ProductRequest;
import com.perryfyback.perryfy.models.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<ProductResponse>> getAllProducts();
    ResponseEntity<ProductResponse> getProductById(Integer productId);
    ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest);
    ResponseEntity<ProductResponse> updateProduct(Integer productId, ProductRequest productRequest);
    ResponseEntity<Void> deleteProduct(Integer productId);
}
