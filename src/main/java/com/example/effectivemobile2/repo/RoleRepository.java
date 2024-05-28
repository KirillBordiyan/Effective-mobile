package com.example.effectivemobile2.repo;

import com.example.effectivemobile2.entity.role.Role;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameRole(String nameRole);
}
