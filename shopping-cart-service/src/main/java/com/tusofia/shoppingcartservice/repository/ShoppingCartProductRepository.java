package com.tusofia.shoppingcartservice.repository;

import com.tusofia.shoppingcartservice.domain.entity.ShoppingCart;
import com.tusofia.shoppingcartservice.domain.entity.ShoppingCartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, String> {
    List<ShoppingCartProduct> findAllByShoppingCart(ShoppingCart shoppingCart);

    List<ShoppingCartProduct> findAllByShoppingCart_Id(String id);

    void deleteAllByShoppingCart_Id(String id);

    Optional<ShoppingCartProduct> findByProductIdAndShoppingCart(String productId, ShoppingCart shoppingCart);
}
