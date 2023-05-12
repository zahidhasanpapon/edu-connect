package com.example.education.educonnect.service;

import com.example.education.educonnect.entity.UserEntity;
import com.example.education.educonnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    // Get all users from the repository
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        users.forEach(student -> student.setPassword(null)); // Removing passwords for security reasons
        return users;
    }

}
