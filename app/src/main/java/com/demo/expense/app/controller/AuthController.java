package com.demo.expense.app.controller;

import com.demo.expense.common.dto.UserDto;
import com.demo.expense.common.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final SecretKey key = Keys.hmacShaKeyFor("change-me-to-32-bytes-secret-change-me".getBytes());

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto dto) {
        UserDto saved = userService.register(dto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto dto) {
        return userService.findByEmail(dto.getEmail())
                .map(u -> {
                    String jwt = Jwts.builder()
                            .setSubject(String.valueOf(u.getId()))
                            .claim("email", u.getEmail())
                            .setIssuedAt(new Date())
                            .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                            .signWith(key, SignatureAlgorithm.HS256)
                            .compact();
                    return ResponseEntity.ok(Map.of("token", jwt));
                })
                .orElse(ResponseEntity.status(401).build());
    }
}