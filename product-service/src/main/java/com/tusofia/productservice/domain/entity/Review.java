package com.tusofia.productservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "user", nullable = false)
    private String user;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "added_on", nullable = false)
    private LocalDateTime addedOn;

    @ManyToOne
    @JoinColumn(name = "fk_product")
    private Product product;
}
