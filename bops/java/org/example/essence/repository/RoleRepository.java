package org.example.essence.repository;

import org.example.essence.Role;
import org.example.essence.enums_status.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByRoleName(UserRole roleName);
}
