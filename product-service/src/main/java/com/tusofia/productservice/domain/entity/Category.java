package com.tusofia.productservice.domain.entity;

import com.tusofia.productservice.domain.enums.CategoryName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    private CategoryName name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
