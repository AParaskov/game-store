package com.tusofia.productservice.service.impl;

import com.tusofia.productservice.domain.entity.Product;
import com.tusofia.productservice.domain.enums.CategoryName;
import com.tusofia.productservice.dto.AddProductRequest;
import com.tusofia.productservice.repository.CategoryRepository;
import com.tusofia.productservice.repository.ProductRepository;
import com.tusofia.productservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public void addProduct(AddProductRequest addProductRequest) {
        Product product = Product.builder()
                .name(addProductRequest.getName())
                .manufacturer(addProductRequest.getManufacturer())
                .description(addProductRequest.getDescription())
                .quantity(addProductRequest.getQuantity())
                .price(addProductRequest.getPrice())
                .category(categoryRepository.findByName(CategoryName.valueOf(addProductRequest.getCategory())))
                .build();

        productRepository.saveAndFlush(product);
    }

    @Override
    public void updateProduct(String name, String quantity) {
        Product product = productRepository.findByName(name);

        product.setQuantity(Integer.parseInt(quantity) + product.getQuantity());
        productRepository.saveAndFlush(product);
    }

    @Transactional
    @Override
    public void updateProductQuantities(List<String> productIds, List<String> productQuantities) {
        productIds.forEach(productId -> {
            String noBracketsProductId = productId.replaceAll("[\\[\\](){}]","");
            Product product = productRepository.findById(noBracketsProductId).get();

            String noBracketsQuantity = productQuantities.get(0).replaceAll("[\\[\\](){}]","");

            product.setQuantity(product.getQuantity() - Integer.parseInt(noBracketsQuantity));
            productQuantities.remove(0);
        });
    }
}
