package com.perryfyback.perryfy.services.Product;

import com.perryfyback.perryfy.entities.*;
import com.perryfyback.perryfy.models.products.ColorResponse;
import com.perryfyback.perryfy.models.products.PrintAreaRequest;
import com.perryfyback.perryfy.models.products.PrintAreaResponse;
import com.perryfyback.perryfy.models.products.ProductRequest;
import com.perryfyback.perryfy.models.products.ProductResponse;
import com.perryfyback.perryfy.repositories.*;
import com.perryfyback.perryfy.services.User.StripeUserService;
import com.stripe.exception.StripeException;
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
public class ProductServiceImpl implements ProductService {

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

    @Autowired
    private StripeUserService stripeUserService;

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
            
            if (productRequest.getPrintAreas() != null) {
                Set<PrintArea> printAreas = new HashSet<>();
                for (PrintAreaRequest printAreaRequest : productRequest.getPrintAreas()) {
                    Optional<PrintArea> existing = printAreaRepository.findByWidthAndHeight(
                        printAreaRequest.getWidth(), printAreaRequest.getHeight()
                    );
                    PrintArea printArea;
                    if (existing.isPresent()) {
                        printArea = existing.get();
                    } else {
                        printArea = new PrintArea();
                        printArea.setWidth(printAreaRequest.getWidth());
                        printArea.setHeight(printAreaRequest.getHeight());
                        printArea = printAreaRepository.save(printArea);
                    }
                    printAreas.add(printArea);
                }
                product.setPrintAreas(printAreas);
            }
            
            Product savedProduct = productRepository.save(product);
            
            // Create product in Stripe
            try {
                var stripeProduct = stripeUserService.createStripeProduct(
                    savedProduct.getProduct_name(),
                    savedProduct.getDescription()
                );
                
                var stripePrice = stripeUserService.createStripePrice(
                    stripeProduct.getId(),
                    savedProduct.getPrice().longValue(),
                    savedProduct.getCurrency()
                );
                
                System.out.println("Product created in Stripe - Product ID: " + stripeProduct.getId() + ", Price ID: " + stripePrice.getId());
                
            } catch (StripeException e) {
                System.err.println("Error creating product in Stripe: " + e.getMessage());
                // Continue with database save even if Stripe fails
            }
            
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
                    Optional<PrintArea> existing = printAreaRepository.findByWidthAndHeight(
                        printAreaRequest.getWidth(), printAreaRequest.getHeight()
                    );
                    PrintArea printArea;
                    if (existing.isPresent()) {
                        printArea = existing.get();
                    } else {
                        printArea = new PrintArea();
                        printArea.setWidth(printAreaRequest.getWidth());
                        printArea.setHeight(printAreaRequest.getHeight());
                        printArea = printAreaRepository.save(printArea);
                    }
                    printAreas.add(printArea);
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
        response.setProductId(product.getProductId());
        response.setProduct_name(product.getProduct_name());
        response.setPrice(product.getPrice().longValue());
        response.setCurrency(product.getCurrency());
        response.setDescription(product.getDescription());
        response.setStock(product.getStock().longValue());
        response.setPrintfulProductId(product.getPrintfulProductId());
        response.setArea_height(product.getAreaHeight());
        response.setArea_width(product.getAreaWidth());
        response.setImageUrls(product.getImages().stream().map(Image::getImage).collect(Collectors.toList()));
        response.setCategories(product.getCategories().stream().map(Category::getCategory).collect(Collectors.toList()));
        response.setColors(product.getColors().stream().map(color -> new ColorResponse(color.getColor_id(), color.getColor(), color.getHexadecimal())).collect(Collectors.toList()));
        response.setSizes(product.getSizes().stream().map(Size::getSize).collect(Collectors.toList()));
        response.setPrintAreas(product.getPrintAreas().stream().map(printArea -> new PrintAreaResponse(printArea.getPrint_area_id(), printArea.getWidth(), printArea.getHeight())).collect(Collectors.toList()));
        return response;
    }   

    private void mapProductRequestToEntity(ProductRequest request, Product product) {
        product.setProduct_name(request.getProduct_name());
        product.setPrice(java.math.BigDecimal.valueOf(request.getPrice()));
        product.setCurrency(request.getCurrency());
        product.setDescription(request.getDescription());
        product.setStock((int) request.getStock());
        if (request.getPrintfulProductId() != null) {
            product.setPrintfulProductId(request.getPrintfulProductId());
        }
        // Nuevos campos para área de impresión
        if (request.getPrintAreas() != null && !request.getPrintAreas().isEmpty()) {
            // Tomar el primer área como principal para los campos directos
            var printArea = request.getPrintAreas().get(0);
            if (printArea.getWidth() != null) {
                product.setAreaWidth(printArea.getWidth());
            }
            if (printArea.getHeight() != null) {
                product.setAreaHeight(printArea.getHeight());
            }
        }
    }
} 