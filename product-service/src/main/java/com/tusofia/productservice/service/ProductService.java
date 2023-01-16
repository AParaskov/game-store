package com.tusofia.productservice.service;

import com.tusofia.productservice.dto.AddProductRequest;

import java.util.List;

public interface ProductService {
    void addProduct(AddProductRequest addProductRequest);

    void updateProduct(String name, String quantity);

    void updateProductQuantities(List<String> productIds, List<String> productQuantities);
}
