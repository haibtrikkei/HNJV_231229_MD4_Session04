package org.example.api_security_jwt.repository;

import org.example.api_security_jwt.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findRoleByRoleName(String rolleName);
}
