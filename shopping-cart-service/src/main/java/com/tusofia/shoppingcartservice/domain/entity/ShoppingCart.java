package com.tusofia.shoppingcartservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "shopping_cart")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartProduct> products;
}
