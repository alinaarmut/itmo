package org.example.essence.repository;

import org.example.essence.User;
import org.example.essence.enums_status.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String username);
    Optional<User> findByRoles_RoleName(UserRole roleName);


}
