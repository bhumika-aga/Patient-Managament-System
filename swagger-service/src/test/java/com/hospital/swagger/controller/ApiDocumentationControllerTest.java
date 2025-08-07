package com.hospital.swagger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiDocumentationController.class)
@ActiveProfiles("test")
class ApiDocumentationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllServices_ShouldReturnServiceEndpoints() throws Exception {
        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.authorization.name").value("Authorization Service"))
                .andExpect(jsonPath("$.['treatment-offering'].name").value("IPTreatment Offering Service"))
                .andExpect(jsonPath("$.['treatment-timetable'].name").value("IPTreatment Service"))
                .andExpect(jsonPath("$.insurance.name").value("Insurance Claim Service"));
    }

    @Test
    void getHealth_ShouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("Swagger Documentation Service"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void getServiceInfo_ShouldReturnSystemInformation() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("MediFlow - International Patient Treatment Management System"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.features.authentication").exists())
                .andExpect(jsonPath("$.features.['treatment-packages']").exists())
                .andExpect(jsonPath("$.features.insurance").exists());
    }
}