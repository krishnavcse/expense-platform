package com.demo.expense.app.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void key_shouldBeCreatedFromDefaultSecret() {
        JwtUtil util = new JwtUtil("ZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtlLWZha2UtZmFrZS1mYWtl");
        assertNotNull(util.getKey());
    }
}