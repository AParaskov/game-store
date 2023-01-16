package com.tusofia.userservice.domain.entity;

import com.tusofia.userservice.domain.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(value = EnumType.STRING)
    private RoleName name;
}
