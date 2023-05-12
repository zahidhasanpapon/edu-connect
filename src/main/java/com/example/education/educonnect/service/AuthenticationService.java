package com.example.education.educonnect.service;

import com.example.education.educonnect.entity.UserEntity;
import com.example.education.educonnect.exception.BadRequest;
import com.example.education.educonnect.model.AuthenticationResponse;
import com.example.education.educonnect.model.payload.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JsonWebTokenService jsonWebTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationResponse authenticate(LoginRequest loginRequest, BindingResult bindingResult) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            Map<String, String> bindingErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                bindingErrors.put(error.getField(), error.getDefaultMessage());
            }
            return AuthenticationResponse.error(HttpStatus.BAD_REQUEST, "Validation error", bindingErrors);
        }

        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Retrieve user entity
            UserEntity userEntity = userService.getUserByEmail(loginRequest.getEmail());
            if (userEntity == null) {
                throw new BadRequest();
            }

            // Generate JWT token
            String jwtToken = jsonWebTokenService.generateToken(authentication);

            // Return authentication response
            return AuthenticationResponse.success(HttpStatus.OK, "Authentication successful", jwtToken);
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return AuthenticationResponse.error(HttpStatus.UNAUTHORIZED, "Authentication failed", null);
        }
    }

}


