package com.example.education.educonnect.entity;

import lombok.*;
import jakarta.persistence.*;

import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseUserEntity {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(min = 11, max = 13)
    @Column(name = "phone")
    private String phone;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    protected BaseUserEntity() {
        // Default constructor is required by JPA specification.
        // It can be safely marked as protected to prevent direct instantiation.
    }

    // Constructor with required fields
    public BaseUserEntity(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isActive = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
