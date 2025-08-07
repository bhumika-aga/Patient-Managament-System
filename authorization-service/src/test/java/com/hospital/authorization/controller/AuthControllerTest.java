package com.hospital.authorization.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.authorization.dto.TokenRequest;
import com.hospital.authorization.dto.TokenResponse;
import com.hospital.authorization.service.AuthService;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void generateToken_WithValidRequest_ReturnsTokenResponse() throws Exception {
        // Given
        TokenRequest request = new TokenRequest("testuser");
        TokenResponse response = new TokenResponse("mock.jwt.token", "Bearer", 1800L);

        when(authService.generateToken(any(TokenRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/auth/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("mock.jwt.token"))
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.expiresIn").value(1800));
    }

    @Test
    void generateToken_WithoutRequestBody_ReturnsTokenResponse() throws Exception {
        // Given
        TokenResponse response = new TokenResponse("mock.jwt.token", "Bearer", 1800L);

        when(authService.generateToken(any(TokenRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/auth/generate-token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("mock.jwt.token"));
    }

    @Test
    void validateToken_WithValidToken_ReturnsTrue() throws Exception {
        // Given
        String token = "valid.jwt.token";
        when(authService.validateToken(token)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/auth/validate")
                .param("token", token))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void validateToken_WithInvalidToken_ReturnsFalse() throws Exception {
        // Given
        String token = "invalid.jwt.token";
        when(authService.validateToken(token)).thenReturn(false);

        // When & Then
        mockMvc.perform(get("/auth/validate")
                .param("token", token))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void health_ReturnsHealthMessage() throws Exception {
        // When & Then
        mockMvc.perform(get("/auth/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Authorization Service is running"));
    }
}