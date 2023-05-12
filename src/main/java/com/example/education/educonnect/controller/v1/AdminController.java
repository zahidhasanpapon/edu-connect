package com.example.education.educonnect.controller.v1;

import com.example.education.educonnect.entity.RoleEntity;
import com.example.education.educonnect.entity.UserEntity;
import com.example.education.educonnect.entity.UserRoleEntity;
import com.example.education.educonnect.repository.RoleRepository;
import com.example.education.educonnect.repository.UserRepository;
import com.example.education.educonnect.repository.UserRoleRepository;
import com.example.education.educonnect.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserEntity>> allUsers() {
        List<UserEntity> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/{id}/roles")
    public ResponseEntity<String> addRolesToUser(@PathVariable Long id, @RequestParam("role") String role) {
        try {
            // Find the user by ID
            Optional<UserEntity> optionalUser = userRepository.findById(id);
            System.out.println(optionalUser);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            UserEntity user = optionalUser.get();
            System.out.println(user);

            // Check if the role is valid (e.g., "teacher" or "student")
            if (!role.equalsIgnoreCase("teacher") && !role.equalsIgnoreCase("student")) {
                return ResponseEntity.badRequest().body("Invalid role");
            }

            // Find the role from the database
            Optional<RoleEntity> optionalRole = roleRepository.findByName(role);
            if(optionalRole.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            System.out.println(optionalRole);

            RoleEntity roleEntity = optionalRole.get();

            UserRoleEntity userRole = UserRoleEntity.builder()
                    .user(user)
                    .role(roleEntity)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRoleRepository.save(userRole);

            return ResponseEntity.ok("Role added successfully");

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<String> updateUserStatus(@PathVariable Long id, @RequestBody String isActive) throws ChangeSetPersister.NotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (isActive.equalsIgnoreCase("true")) {
            user.setIsActive(Boolean.TRUE);
        } else {
            user.setIsActive(Boolean.FALSE);
        }

        userRepository.save(user);

        String status = user.getIsActive() ? "activated" : "deactivated";
        return ResponseEntity.ok("User " + status + " successfully.");
    }

}
