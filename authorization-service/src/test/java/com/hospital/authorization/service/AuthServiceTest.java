package com.hospital.authorization.service;

import com.hospital.authorization.dto.TokenRequest;
import com.hospital.authorization.dto.TokenResponse;
import com.hospital.authorization.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void generateToken_WithValidRequest_ReturnsTokenResponse() {
        // Given
        TokenRequest request = new TokenRequest("testuser");
        String mockToken = "mock.jwt.token";
        Long mockExpiration = 1800000L;

        when(jwtUtil.generateToken("testuser")).thenReturn(mockToken);
        when(jwtUtil.getExpirationTime()).thenReturn(mockExpiration);

        // When
        TokenResponse response = authService.generateToken(request);

        // Then
        assertNotNull(response);
        assertEquals(mockToken, response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(1800L, response.getExpiresIn()); // 1800000 / 1000

        verify(jwtUtil).generateToken("testuser");
        verify(jwtUtil).getExpirationTime();
    }

    @Test
    void generateToken_WithNullUsername_UsesAnonymous() {
        // Given
        TokenRequest request = new TokenRequest(null);
        String mockToken = "mock.jwt.token";
        Long mockExpiration = 1800000L;

        when(jwtUtil.generateToken("anonymous")).thenReturn(mockToken);
        when(jwtUtil.getExpirationTime()).thenReturn(mockExpiration);

        // When
        TokenResponse response = authService.generateToken(request);

        // Then
        assertNotNull(response);
        assertEquals(mockToken, response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(1800L, response.getExpiresIn());

        verify(jwtUtil).generateToken("anonymous");
    }

    @Test
    void validateToken_WithValidToken_ReturnsTrue() {
        // Given
        String token = "valid.jwt.token";
        String username = "testuser";

        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.validateToken(token, username)).thenReturn(true);

        // When
        boolean result = authService.validateToken(token);

        // Then
        assertTrue(result);
        verify(jwtUtil).extractUsername(token);
        verify(jwtUtil).validateToken(token, username);
    }

    @Test
    void validateToken_WithInvalidToken_ReturnsFalse() {
        // Given
        String token = "invalid.jwt.token";

        when(jwtUtil.extractUsername(token)).thenThrow(new RuntimeException("Invalid token"));

        // When
        boolean result = authService.validateToken(token);

        // Then
        assertFalse(result);
        verify(jwtUtil).extractUsername(token);
        verify(jwtUtil, never()).validateToken(anyString(), anyString());
    }

    @Test
    void validateToken_WithExpiredToken_ReturnsFalse() {
        // Given
        String token = "expired.jwt.token";
        String username = "testuser";

        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.validateToken(token, username)).thenReturn(false);

        // When
        boolean result = authService.validateToken(token);

        // Then
        assertFalse(result);
        verify(jwtUtil).extractUsername(token);
        verify(jwtUtil).validateToken(token, username);
    }
}