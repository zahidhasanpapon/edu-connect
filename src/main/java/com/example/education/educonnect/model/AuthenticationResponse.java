package com.example.education.educonnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
//@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private HttpStatus status;
    private String message;

    @JsonProperty("access_token")
    private String accessToken;

    public AuthenticationResponse(HttpStatus status, String message, String accessToken) {
        this.status = status;
        this.message = message;
        this.accessToken = accessToken;
    }

    // Helper methods to create response instances
    public static AuthenticationResponse success(HttpStatus status, String message, String accessToken) {
        return new AuthenticationResponse(status, message, accessToken);
    }

    public static AuthenticationResponse error(HttpStatus status, String message, Map<String, String> errors) {
        // You can include the errors map or additional fields in the response as needed
        return new AuthenticationResponse(status, message, null);
    }
}
