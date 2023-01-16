package com.tusofia.shoppingcartservice.repository;

import com.tusofia.shoppingcartservice.domain.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    Optional<ShoppingCart> findByUsername(String username);

    Optional<ShoppingCart> findById(String id);
}
