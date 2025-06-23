package com.perryfyback.perryfy.services.Product;

import org.springframework.http.ResponseEntity;

import com.perryfyback.perryfy.models.products.ProductRequest;
import com.perryfyback.perryfy.models.products.ProductResponse;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<ProductResponse>> getAllProducts();
    ResponseEntity<ProductResponse> getProductById(Integer productId);
    ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest);
    ResponseEntity<ProductResponse> updateProduct(Integer productId, ProductRequest productRequest);
    ResponseEntity<Void> deleteProduct(Integer productId);
} 