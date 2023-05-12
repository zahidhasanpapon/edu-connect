package com.example.education.educonnect.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JsonWebTokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder jwtDecoder;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Jwt::getSubject);
    }

    public <T> T extractClaim(String token, Function<Jwt, T> claimsResolver) {
        Jwt jwt = jwtDecoder.decode(token);
        return claimsResolver.apply(jwt);
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = Date.from(extractExpiration(token));
        return expiration.before(new Date());
    }

    public Instant extractExpiration(String token) {
        return extractClaim(token, AbstractOAuth2Token::getExpiresAt);
    }

    public Boolean validateToken(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}
