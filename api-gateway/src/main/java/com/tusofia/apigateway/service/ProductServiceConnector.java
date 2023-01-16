package com.tusofia.apigateway.service;

import com.tusofia.apigateway.dto.*;

import java.util.List;

public interface ProductServiceConnector {
    ProductDTO getProductById(String id);

    List<ProductDTO> getAllProducts();

    void deleteProduct(String id);

    void addProduct(ProductAddRequest productAddRequest, String category);

    void updateProduct(ProductUpdateRequest productUpdateRequest);

    List<String> getAllCategoryNames();

    void updateProductQuantity(UpdateProductQuantityDTO updateProductQuantityDTO);

    void addReview(ReviewAddRequest reviewAddRequest, String username);


}
