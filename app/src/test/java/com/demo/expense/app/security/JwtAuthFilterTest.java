package com.demo.expense.app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthFilterTest {

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilter_setsAuthenticationWhenValidToken() throws ServletException, IOException {
        JwtUtil util = new JwtUtil("ZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtl");
        String token = Jwts.builder()
                .setSubject("123")
                .signWith(util.getKey(), SignatureAlgorithm.HS256)
                .compact();

        JwtAuthFilter filter = new JwtAuthFilter(util);
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, res, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("123", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void doFilter_ignoresWhenNoHeader() throws ServletException, IOException {
        JwtUtil util = new JwtUtil("ZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtl");
        JwtAuthFilter filter = new JwtAuthFilter(util);
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(req, res, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}