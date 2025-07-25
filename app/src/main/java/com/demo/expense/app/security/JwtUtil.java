package com.demo.expense.app.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private final SecretKey key;

    public JwtUtil(@Value("${security.jwt.secret-base64:ZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtl}") String base64) {
        byte[] bytes = Decoders.BASE64.decode(base64);
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public SecretKey getKey() {
        return key;
    }
}