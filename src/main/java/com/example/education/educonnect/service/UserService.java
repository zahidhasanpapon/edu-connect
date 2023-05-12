package com.example.education.educonnect.service;

import com.example.education.educonnect.entity.BaseUserEntity;
import com.example.education.educonnect.entity.UserEntity;
import com.example.education.educonnect.exception.InternalServerError;
import com.example.education.educonnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserByEmail(String email) {
        UserEntity userEntity;

        try {
            userEntity = this.userRepository.findByEmail(email);
        } catch (Exception ex) {
            // TODO: proper error handling
            throw new InternalServerError();
        }

        return userEntity;
    }

    public Optional<UserEntity> getUserById(Long id) {
        Optional<UserEntity> userEntity;

        try {
            userEntity = this.userRepository.findById(id);
        } catch (Exception ex) {
            // TODO: proper error handling
            throw new InternalServerError();
        }

        return userEntity;
    }

    public UserEntity createUser(UserEntity currUserEntity) {
        try {
            return this.userRepository.save(currUserEntity);
        } catch (Exception ex) {
            throw new InternalServerError();
        }
    }

}
