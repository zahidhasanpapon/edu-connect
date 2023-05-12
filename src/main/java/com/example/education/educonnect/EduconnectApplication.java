package com.example.education.educonnect;

import com.example.education.educonnect.entity.*;
import com.example.education.educonnect.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class EduconnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduconnectApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner commandLineRunner(
            RoleRepository roleRepository,
            UserRepository userRepository,
            AdminRepository adminRepository,
            UserRoleRepository userRoleRepository,
            DepartmentRepository departmentRepository
    ) {
        return args -> {
            createDepartments(departmentRepository);
            createRoles(roleRepository);
            createUserAndAdmin(roleRepository, userRepository, adminRepository, userRoleRepository);
        };
    }

    // Create and save departments
    private void createDepartments(DepartmentRepository departmentRepository) {
        List<DepartmentEntity> departments = List.of(
                DepartmentEntity.builder().name("CSE")
                        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                DepartmentEntity.builder().name("ETE")
                        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                DepartmentEntity.builder().name("EEE")
                        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build()
        );
        departmentRepository.saveAll(departments);
    }

    // Create and save the roles
    private void createRoles(RoleRepository roleRepository) {
        List<RoleEntity> roles = List.of(
                RoleEntity.builder().name("admin")
                        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                RoleEntity.builder().name("teacher")
                        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build(),
                RoleEntity.builder().name("student")
                        .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build()
        );
        roleRepository.saveAll(roles);
    }

    // Create a new user "ADMIN" and associated admin entity
    private void createUserAndAdmin(
            RoleRepository roleRepository,
            UserRepository userRepository,
            AdminRepository adminRepository,
            UserRoleRepository userRoleRepository
    ) {
        UserEntity user = UserEntity.builder()
                .name("Admin")
                .email("admin@mail.com")
                .phone("01626321101")
                .password("admin@123")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        AdminEntity admin = AdminEntity.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        adminRepository.save(admin);

        RoleEntity adminRole = roleRepository.findByName("admin").orElseThrow();

        UserRoleEntity userRole = UserRoleEntity.builder()
                .user(user)
                .role(adminRole)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRoleRepository.save(userRole);
    }

}
