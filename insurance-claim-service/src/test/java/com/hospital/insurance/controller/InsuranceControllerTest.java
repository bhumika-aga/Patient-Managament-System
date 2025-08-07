package com.hospital.insurance.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.insurance.dto.ClaimInitiationRequest;
import com.hospital.insurance.dto.ClaimInitiationResponse;
import com.hospital.insurance.dto.InsurerDTO;
import com.hospital.insurance.entity.ClaimRequest;
import com.hospital.insurance.service.InsuranceClaimService;

@WebMvcTest(InsuranceController.class)
@ActiveProfiles("test")
class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InsuranceClaimService insuranceClaimService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClaimRequest sampleClaimRequest;
    private InsurerDTO sampleInsurer;
    private ClaimInitiationRequest sampleInitiationRequest;
    private ClaimInitiationResponse sampleInitiationResponse;

    @BeforeEach
    void setUp() {
        sampleInsurer = new InsurerDTO();
        sampleInsurer.setId(1L);
        sampleInsurer.setInsurerName("Health Insurance Corp");
        sampleInsurer.setPackageName("Premium Health Package");
        sampleInsurer.setInsuranceAmountLimit(500000.0);
        sampleInsurer.setDisbursementDurationDays(7);
        sampleInsurer.setContactEmail("contact@healthcorp.com");
        sampleInsurer.setContactPhone("123-456-7890");
        sampleInsurer.setAddress("123 Insurance St, City, State");
        sampleInsurer.setWebsite("www.healthcorp.com");
        sampleInsurer.setActive(true);

        sampleClaimRequest = new ClaimRequest("John Doe", "Heart Disease", "Cardiology Package", 
                                            25000.0, "Health Insurance Corp", "Premium Health Package", 
                                            500000.0, 0.0);
        sampleClaimRequest.setId(1L);
        sampleClaimRequest.setClaimStatus("INITIATED");
        sampleClaimRequest.setClaimInitiatedDate(LocalDateTime.now());

        sampleInitiationRequest = new ClaimInitiationRequest();
        sampleInitiationRequest.setPatientName("John Doe");
        sampleInitiationRequest.setAilment("Heart Disease");
        sampleInitiationRequest.setTreatmentPackageName("Cardiology Package");
        sampleInitiationRequest.setInsurerName("Health Insurance Corp");
        sampleInitiationRequest.setTreatmentCost(25000.0);
        sampleInitiationRequest.setPatientId(1L);

        sampleInitiationResponse = new ClaimInitiationResponse(1L, "MF-HEA-20240101120000", 
                                                              "John Doe", "Health Insurance Corp", 
                                                              25000.0, 25000.0, 0.0, "INITIATED",
                                                              LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                                                              "Claim initiated successfully");
    }

    @Test
    void getAllInsurerDetails_ShouldReturnInsurerList() throws Exception {
        List<InsurerDTO> insurers = Arrays.asList(sampleInsurer);
        when(insuranceClaimService.getAllInsurerDetails()).thenReturn(insurers);

        mockMvc.perform(get("/GetAllInsurerDetail"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].insurerName").value("Health Insurance Corp"))
                .andExpect(jsonPath("$[0].insuranceAmountLimit").value(500000.0));
    }

    @Test
    void getInsurerByPackageName_ShouldReturnInsurer_WhenValidPackage() throws Exception {
        when(insuranceClaimService.getInsurerByPackageName("Orthopaedics")).thenReturn(sampleInsurer);

        mockMvc.perform(get("/GetInsurerByPackageName")
                        .param("packageName", "Orthopaedics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.insurerName").value("Health Insurance Corp"))
                .andExpect(jsonPath("$.insuranceAmountLimit").value(500000.0));
    }

    @Test
    void initiateClaim_ShouldReturnClaimResponse_WhenValidInput() throws Exception {
        when(insuranceClaimService.initiateClaim(any(ClaimInitiationRequest.class)))
                .thenReturn(sampleInitiationResponse);

        mockMvc.perform(post("/InitiateClaim")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleInitiationRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patientName").value("John Doe"))
                .andExpect(jsonPath("$.treatmentCost").value(25000.0))
                .andExpect(jsonPath("$.claimStatus").value("INITIATED"))
                .andExpect(jsonPath("$.insuranceCoverage").value(25000.0));
    }

    @Test
    void getAllClaims_ShouldReturnClaimList() throws Exception {
        List<ClaimRequest> claims = Arrays.asList(sampleClaimRequest);
        when(insuranceClaimService.getClaimsByStatus("INITIATED")).thenReturn(claims);

        mockMvc.perform(get("/claims"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].patientName").value("John Doe"))
                .andExpect(jsonPath("$[0].claimStatus").value("INITIATED"));
    }

    @Test
    void getClaimsByStatus_ShouldReturnFilteredClaims() throws Exception {
        List<ClaimRequest> claims = Arrays.asList(sampleClaimRequest);
        when(insuranceClaimService.getClaimsByStatus("INITIATED")).thenReturn(claims);

        mockMvc.perform(get("/claims/status/INITIATED"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].patientName").value("John Doe"));
    }

    @Test
    void getClaimByPatientId_ShouldReturnClaim() throws Exception {
        when(insuranceClaimService.getClaimByPatientId(1L)).thenReturn(sampleClaimRequest);

        mockMvc.perform(get("/claims/patient/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patientName").value("John Doe"));
    }

    @Test
    void getClaimByReferenceNumber_ShouldReturnClaim() throws Exception {
        when(insuranceClaimService.getClaimByReferenceNumber("MF-HEA-20240101120000"))
                .thenReturn(sampleClaimRequest);

        mockMvc.perform(get("/claims/reference/MF-HEA-20240101120000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patientName").value("John Doe"));
    }

    @Test
    void updateClaimStatus_ShouldReturnUpdatedClaim() throws Exception {
        ClaimRequest updatedClaim = sampleClaimRequest;
        updatedClaim.setClaimStatus("APPROVED");
        when(insuranceClaimService.updateClaimStatus(1L, "APPROVED")).thenReturn(updatedClaim);

        mockMvc.perform(put("/claims/1/status")
                        .param("status", "APPROVED"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.claimStatus").value("APPROVED"));
    }

    @Test
    void getInsuranceInfo_ShouldReturnSystemInfo() throws Exception {
        when(insuranceClaimService.getAllInsurerDetails()).thenReturn(Arrays.asList(sampleInsurer));

        mockMvc.perform(get("/insurance-info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.supportedSpecializations").exists())
                .andExpect(jsonPath("$.totalActiveInsurers").value(1));
    }

    @Test
    void health_ShouldReturnHealthStatus() throws Exception {
        when(insuranceClaimService.getAllInsurerDetails()).thenReturn(Arrays.asList(sampleInsurer));

        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("InsuranceClaim Service"));
    }
}