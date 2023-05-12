package com.example.education.educonnect.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "_user")
public class UserEntity extends BaseUserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Implement UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's authorities (roles/permissions) here
        // You can define a separate entity to represent authorities and establish a relationship with the user entity
        // For simplicity, let's assume the user has a single authority "ROLE_USER"
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        // Return the username of the user
        return getEmail(); // Assuming getEmail() returns the username/email
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if the user account is not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if the user account is not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the user credentials are not expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Return true if the user is enabled
        return true;
    }

    public void isActive(Boolean val) {
        setIsActive(true);
    }
}
