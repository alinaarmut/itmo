package org.example.essence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.essence.enums_status.UserRole;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}