package com.tusofia.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tusofia.productservice.domain.entity.Product;
import com.tusofia.productservice.dto.AddProductRequest;
import com.tusofia.productservice.dto.AddReviewRequest;
import com.tusofia.productservice.dto.GetProductByIdDTO;
import com.tusofia.productservice.dto.ReviewDTO;
import com.tusofia.productservice.repository.CategoryRepository;
import com.tusofia.productservice.repository.ProductRepository;
import com.tusofia.productservice.repository.ReviewRepository;
import com.tusofia.productservice.service.ProductService;
import com.tusofia.productservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/internal/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping(value = "/get-product-by-id", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getProductById(@RequestParam String id) throws JsonProcessingException {
        Product product = productRepository.findById(id).get();

        return objectMapper.writeValueAsString(GetProductByIdDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .manufacturer(product.getManufacturer())
                .price(product.getPrice())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .category(product.getCategory().getName().name())
                .imgUrl(String.format("/img/%s-%s.jpg", product.getName(),
                        product.getCategory().getName().name()))
                .reviews(product.getReviews().stream()
                        .map(review -> ReviewDTO.builder()
                                .userName(review.getUser())
                                .text(review.getText())
                                .addedOn(review.getAddedOn().toString())
                                .productId(review.getProduct().getName())
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }

    @GetMapping(value = "/delete-product-by-id", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteProductById(@RequestParam String id) {
        productRepository.deleteById(id);
    }

    @GetMapping(value = "/get-all-products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> GetProductByIdDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .manufacturer(product.getManufacturer())
                                .price(product.getPrice())
                                .description(product.getDescription())
                                .reviews(product.getReviews().stream()
                                        .map(review -> ReviewDTO.builder()
                                                .userName(review.getUser())
                                                .text(review.getText())
                                                .addedOn(review.getAddedOn().toString())
                                                .productId(review.getProduct().getName())
                                                .build())
                                        .collect(Collectors.toList()))
                                .quantity(product.getQuantity())
                                .imgUrl(String.format("/img/%s-%s.jpg", product.getName(),
                                        product.getCategory().getName().name()))
                                .build()
                ).collect(Collectors.toList());
    }

    @PostMapping(value = "/add-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProduct(@RequestBody Object addProductRequest, @RequestParam String category) {
        Map<String, Object> requestBody = (Map<String, Object>) addProductRequest;
        String quantityAsString = requestBody.get("quantity").toString();
        AddProductRequest request = AddProductRequest.builder()
                .name(requestBody.get("name").toString())
                .manufacturer(requestBody.get("manufacturer").toString())
                .description(requestBody.get("description").toString())
                .quantity(Integer.parseInt(quantityAsString))
                .category(category)
                .price(BigDecimal.valueOf(Long.parseLong(requestBody.get("price").toString())))
                .build();
        productService.addProduct(request);
    }

    @PostMapping(value = "/add-review", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addReview(@RequestParam String username, @RequestParam String text, @RequestParam String productId) {
        AddReviewRequest request = AddReviewRequest.builder()
                .text(text)
                .productId(productId)
                .build();
        reviewService.addReview(username, request);
    }

    @PutMapping(value = "/update-product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProduct(@RequestParam String name, @RequestParam String quantity) {
        productService.updateProduct(name, quantity);
    }

    @PutMapping(value = "/update-product-quantity", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProductQuantity(@RequestParam List<String> productIds , @RequestParam List<String> productQuantities) {
        productService.updateProductQuantities(productIds, productQuantities);
    }

    @GetMapping(value = "/get-all-category-names", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getAllCategoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> category.getName().name())
                .collect(Collectors.joining(","));
    }
}
