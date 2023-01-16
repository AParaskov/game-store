package com.tusofia.shoppingcartservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Entity
@Table(name = "shopping_cart_products")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ShoppingCartProduct {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "product_quantity")
    private int productQuantity;
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(name = "fk_shopping_cart")
    private ShoppingCart shoppingCart;
}
