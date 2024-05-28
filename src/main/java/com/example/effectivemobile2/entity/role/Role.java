package com.example.effectivemobile2.entity.role;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Enumerated(EnumType.STRING)
    @Column
    private ERole nameRole;
}
