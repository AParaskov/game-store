package com.tusofia.shoppingcartservice.service.impl;

import com.tusofia.shoppingcartservice.domain.entity.ShoppingCart;
import com.tusofia.shoppingcartservice.domain.entity.ShoppingCartProduct;
import com.tusofia.shoppingcartservice.dto.FindAllShoppingCartProductsDTO;
import com.tusofia.shoppingcartservice.dto.UpdateProductQuantityDTO;
import com.tusofia.shoppingcartservice.repository.ShoppingCartProductRepository;
import com.tusofia.shoppingcartservice.repository.ShoppingCartRepository;
import com.tusofia.shoppingcartservice.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartProductRepository shoppingCartProductRepository;

    @Override
    public void addProductToCart(String shoppingCartId,
                                 String productId,
                                 String name,
                                 String description,
                                 String manufacturer,
                                 String quantity,
                                 String productQuantity,
                                 String price,
                                 String category) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findById(shoppingCartId)
                .orElseThrow();

        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();


        if (shoppingCartProductRepository.count() == 0 || shoppingCart.getProducts().size() == 0) {
            shoppingCartProduct.setProductId(productId);
            shoppingCartProduct.setProductQuantity(Integer.parseInt(productQuantity));
            shoppingCartProduct.setQuantity(Integer.parseInt(quantity));
            shoppingCartProduct.setCategory(category);
            shoppingCartProduct.setDescription(description);
            shoppingCartProduct.setManufacturer(manufacturer);
            shoppingCartProduct.setName(name);
            shoppingCartProduct.setPrice(new BigDecimal(price));
            shoppingCartProduct.setShoppingCart(shoppingCart);


            shoppingCart.getProducts().add(shoppingCartProduct);

            this.shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);

        } else {
            if (shoppingCartProductRepository.findByProductIdAndShoppingCart(productId, shoppingCart).isEmpty()) {
                shoppingCartProduct.setProductId(productId);
                shoppingCartProduct.setProductQuantity(Integer.parseInt(productQuantity));
                shoppingCartProduct.setQuantity(Integer.parseInt(quantity));
                shoppingCartProduct.setCategory(category);
                shoppingCartProduct.setDescription(description);
                shoppingCartProduct.setManufacturer(manufacturer);
                shoppingCartProduct.setName(name);
                shoppingCartProduct.setPrice(new BigDecimal(price));
                shoppingCartProduct.setShoppingCart(shoppingCart);


                shoppingCart.getProducts().add(shoppingCartProduct);

                shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);
            } else {
                shoppingCartProduct = this.shoppingCartProductRepository
                        .findByProductIdAndShoppingCart(productId, shoppingCart)
                        .orElseThrow();
                shoppingCartProduct.setQuantity(shoppingCartProduct.getQuantity() + Integer.parseInt(quantity));
                this.shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);

            }
        }
    }

    @Override
    public List<FindAllShoppingCartProductsDTO> findAllShoppingCartProducts(String shoppingCartId) {
        return shoppingCartProductRepository.findAllByShoppingCart_Id(shoppingCartId).stream()
                .map(product -> FindAllShoppingCartProductsDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .manufacturer(product.getManufacturer())
                        .quantity(product.getQuantity())
                        .imgUrl(String.format("/img/%s-%s.jpg", product.getName(),
                                product.getCategory()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void remove(String id) {
        ShoppingCartProduct shoppingCartProduct = this.shoppingCartProductRepository
                .findById(id)
                .orElseThrow();

        if (shoppingCartProduct.getQuantity() > 1) {
            shoppingCartProduct.setQuantity(shoppingCartProduct.getQuantity() - 1);
            this.shoppingCartProductRepository.saveAndFlush(shoppingCartProduct);
        } else {
            this.shoppingCartProductRepository
                    .deleteById(id);
        }
    }

    @Override
    public BigDecimal total(String shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).get();
        BigDecimal sum = new BigDecimal(0);

        for (int i = 0; i < shoppingCart.getProducts().size(); i++) {
            sum = sum.add(new BigDecimal(shoppingCart.getProducts().get(i).getQuantity()).multiply(shoppingCart.getProducts().get(i).getPrice()));
        }

        return sum;
    }

    @Transactional
    @Override
    public UpdateProductQuantityDTO removeAll(String id) {
        UpdateProductQuantityDTO updateProductQuantityDTO = new UpdateProductQuantityDTO();
        shoppingCartProductRepository.findAllByShoppingCart_Id(id)
                .forEach(shoppingCartProduct -> {
                    shoppingCartProduct.setProductQuantity(shoppingCartProduct.getProductQuantity() - shoppingCartProduct.getQuantity());
                    updateProductQuantityDTO.getProductIds().add(shoppingCartProduct.getProductId());
                    updateProductQuantityDTO.getProductQuantities().add(String.valueOf(shoppingCartProduct.getQuantity()));
                });

        shoppingCartProductRepository
                .deleteAllByShoppingCart_Id(id);

        return updateProductQuantityDTO;
    }
}
