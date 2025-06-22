package com.perryfyback.perryfy.services;

import com.perryfyback.perryfy.models.ProductRequest;
import com.perryfyback.perryfy.models.ProducResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<ProducResponse>> getAllProducts();
    ResponseEntity<ProducResponse> getProductById(Integer productId);
    ResponseEntity<ProducResponse> createProduct(ProductRequest productRequest);
    ResponseEntity<ProducResponse> updateProduct(Integer productId, ProductRequest productRequest);
    ResponseEntity<Void> deleteProduct(Integer productId);
}
