package com.example.education.educonnect.controller.v1;

import com.example.education.educonnect.config.PwEncoderConfig;
import com.example.education.educonnect.entity.UserEntity;
import com.example.education.educonnect.exception.Conflict;
import com.example.education.educonnect.model.AuthenticationResponse;
import com.example.education.educonnect.model.RegisterRequest;
import com.example.education.educonnect.model.payload.LoginRequest;
import com.example.education.educonnect.service.AuthenticationService;
import com.example.education.educonnect.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final PwEncoderConfig pwEncoderConfig;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Handle validation errors
            Map<String, String> bindingErrors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                bindingErrors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(bindingErrors);
        }

        if (this.userService.getUserByEmail(registerRequest.getEmail()) != null) {
            throw new Conflict();
        }

        String encodedPassword = this.pwEncoderConfig.passwordEncoder().encode(registerRequest.getPassword());

        var newUser = UserEntity.builder()
                .name(registerRequest.getName())
                .phone(registerRequest.getPhone())
                .email(registerRequest.getEmail())
                .password(encodedPassword)
                .isActive(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        var newlyCreatedUser = this.userService.createUser(newUser);

        return ResponseEntity.ok(newlyCreatedUser);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest, bindingResult));
    }

}
