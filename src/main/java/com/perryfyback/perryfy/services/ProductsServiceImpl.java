package com.perryfyback.perryfy.services;

import com.perryfyback.perryfy.entities.Product;
import com.perryfyback.perryfy.models.ProductRequest;
import com.perryfyback.perryfy.models.ProducResponse;
import com.perryfyback.perryfy.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<List<ProducResponse>> getAllProducts() {
        List<ProducResponse> products = productRepository.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<ProducResponse> getProductById(Integer productId) {
        return productRepository.findById(productId)
                .map(product -> ResponseEntity.ok(mapToProductResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ProducResponse> createProduct(ProductRequest productRequest) {
        Product product = new Product();
        mapProductRequestToEntity(productRequest, product);
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(mapToProductResponse(savedProduct));
    }

    @Override
    public ResponseEntity<ProducResponse> updateProduct(Integer productId, ProductRequest productRequest) {
        return productRepository.findById(productId)
                .map(existingProduct -> {
                    mapProductRequestToEntity(productRequest, existingProduct);
                    Product updatedProduct = productRepository.save(existingProduct);
                    return ResponseEntity.ok(mapToProductResponse(updatedProduct));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer productId) {
        return productRepository.findById(productId)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private ProducResponse mapToProductResponse(Product product) {
        ProducResponse response = new ProducResponse();
        response.setProduct_name(product.getProduct_name());
        response.setPrice(product.getPrice().longValue());
        response.setCurrency(product.getCurrency());
        response.setDescription(product.getDescription());
        response.setStock(product.getStock().longValue());
        return response;
    }

    private void mapProductRequestToEntity(ProductRequest request, Product product) {
        product.setProduct_name(request.getProduct_name());
        product.setPrice(java.math.BigDecimal.valueOf(request.getPrice()));
        product.setCurrency(request.getCurrency());
        product.setDescription(request.getDescription());
        product.setStock((int) request.getStock());
    }
}
