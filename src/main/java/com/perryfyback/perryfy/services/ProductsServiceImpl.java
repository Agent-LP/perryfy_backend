package com.perryfyback.perryfy.services;

import com.perryfyback.perryfy.entities.*;
import com.perryfyback.perryfy.models.ProductRequest;
import com.perryfyback.perryfy.models.PrintAreaRequest;
import com.perryfyback.perryfy.models.ProductResponse;
import com.perryfyback.perryfy.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PrintAreaRepository printAreaRepository;

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productRepository.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(Integer productId) {
        return productRepository.findById(productId)
                .map(product -> ResponseEntity.ok(mapToProductResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {
        try {
            // Create the main product
            Product product = new Product();
            mapProductRequestToEntity(productRequest, product);
            
            // Set related entities
            if (productRequest.getColorIds() != null && !productRequest.getColorIds().isEmpty()) {
                Set<Color> colors = new HashSet<>(colorRepository.findAllById(productRequest.getColorIds()));
                product.setColors(colors);
            }
            
            if (productRequest.getSizeIds() != null && !productRequest.getSizeIds().isEmpty()) {
                Set<Size> sizes = new HashSet<>(sizeRepository.findAllById(productRequest.getSizeIds()));
                product.setSizes(sizes);
            }
            
            if (productRequest.getCategoryIds() != null && !productRequest.getCategoryIds().isEmpty()) {
                Set<Category> categories = new HashSet<>(categoryRepository.findAllById(productRequest.getCategoryIds()));
                product.setCategories(categories);
            }
            
            if (productRequest.getImageUrls() != null && !productRequest.getImageUrls().isEmpty()) {
                Set<Image> images = new HashSet<>();
                for (String imageUrl : productRequest.getImageUrls()) {
                    Image image = new Image();
                    image.setImage(imageUrl);
                    Image savedImage = imageRepository.save(image);
                    images.add(savedImage);
                }
                product.setImages(images);
            }
            
            if (productRequest.getPrintAreas() != null && !productRequest.getPrintAreas().isEmpty()) {
                Set<PrintArea> printAreas = new HashSet<>();
                for (PrintAreaRequest printAreaRequest : productRequest.getPrintAreas()) {
                    PrintArea printArea = new PrintArea();
                    printArea.setWidth(printAreaRequest.getWidth());
                    printArea.setHeight(printAreaRequest.getHeight());
                    PrintArea savedPrintArea = printAreaRepository.save(printArea);
                    printAreas.add(savedPrintArea);
                }
                product.setPrintAreas(printAreas);
            }
            
            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(mapToProductResponse(savedProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponse> updateProduct(Integer productId, ProductRequest productRequest) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Product existingProduct = optionalProduct.get();
        
        try {
            mapProductRequestToEntity(productRequest, existingProduct);
            
            // Update related entities
            if (productRequest.getColorIds() != null) {
                Set<Color> colors = new HashSet<>(colorRepository.findAllById(productRequest.getColorIds()));
                existingProduct.setColors(colors);
            }
            
            if (productRequest.getSizeIds() != null) {
                Set<Size> sizes = new HashSet<>(sizeRepository.findAllById(productRequest.getSizeIds()));
                existingProduct.setSizes(sizes);
            }
            
            if (productRequest.getCategoryIds() != null) {
                Set<Category> categories = new HashSet<>(categoryRepository.findAllById(productRequest.getCategoryIds()));
                existingProduct.setCategories(categories);
            }
            
            if (productRequest.getImageUrls() != null) {
                Set<Image> images = new HashSet<>();
                for (String imageUrl : productRequest.getImageUrls()) {
                    Image image = new Image();
                    image.setImage(imageUrl);
                    Image savedImage = imageRepository.save(image);
                    images.add(savedImage);
                }
                existingProduct.setImages(images);
            }
            
            if (productRequest.getPrintAreas() != null) {
                Set<PrintArea> printAreas = new HashSet<>();
                for (PrintAreaRequest printAreaRequest : productRequest.getPrintAreas()) {
                    PrintArea printArea = new PrintArea();
                    printArea.setWidth(printAreaRequest.getWidth());
                    printArea.setHeight(printAreaRequest.getHeight());
                    PrintArea savedPrintArea = printAreaRepository.save(printArea);
                    printAreas.add(savedPrintArea);
                }
                existingProduct.setPrintAreas(printAreas);
            }
            
            Product updatedProduct = productRepository.save(existingProduct);
            return ResponseEntity.ok(mapToProductResponse(updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
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
