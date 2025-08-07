package com.hospital.authorization.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String testSecret = "testSecretKeyForJWTTokenGenerationAndValidation2024";
    private final Long testExpiration = 1800000L; // 30 minutes

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", testSecret);
        ReflectionTestUtils.setField(jwtUtil, "expiration", testExpiration);
    }

    @Test
    void generateToken_WithValidUsername_ReturnsToken() {
        // Given
        String username = "testuser";

        // When
        String token = jwtUtil.generateToken(username);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void extractUsername_WithValidToken_ReturnsUsername() {
        // Given
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // When
        String extractedUsername = jwtUtil.extractUsername(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    @Test
    void extractExpiration_WithValidToken_ReturnsExpirationDate() {
        // Given
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // When
        Date expirationDate = jwtUtil.extractExpiration(token);

        // Then
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void validateToken_WithValidToken_ReturnsTrue() {
        // Given
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // When
        Boolean isValid = jwtUtil.validateToken(token, username);

        // Then
        assertTrue(isValid);
    }

    @Test
    void validateToken_WithDifferentUsername_ReturnsFalse() {
        // Given
        String username = "testuser";
        String differentUsername = "differentuser";
        String token = jwtUtil.generateToken(username);

        // When
        Boolean isValid = jwtUtil.validateToken(token, differentUsername);

        // Then
        assertFalse(isValid);
    }

    @Test
    void getExpirationTime_ReturnsConfiguredExpiration() {
        // When
        Long expirationTime = jwtUtil.getExpirationTime();

        // Then
        assertEquals(testExpiration, expirationTime);
    }

    @Test
    void generateToken_WithNullUsername_ThrowsException() {
        // Given
        String username = null;

        // When & Then
        assertThrows(Exception.class, () -> jwtUtil.generateToken(username));
    }

    @Test
    void extractUsername_WithInvalidToken_ThrowsException() {
        // Given
        String invalidToken = "invalid.token.format";

        // When & Then
        assertThrows(Exception.class, () -> jwtUtil.extractUsername(invalidToken));
    }
}