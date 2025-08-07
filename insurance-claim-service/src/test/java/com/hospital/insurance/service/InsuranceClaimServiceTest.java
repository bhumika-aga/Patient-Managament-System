package com.hospital.insurance.service;

import com.hospital.insurance.dto.ClaimInitiationRequest;
import com.hospital.insurance.dto.ClaimInitiationResponse;
import com.hospital.insurance.dto.InsurerDTO;
import com.hospital.insurance.entity.ClaimRequest;
import com.hospital.insurance.entity.Insurer;
import com.hospital.insurance.repository.ClaimRequestRepository;
import com.hospital.insurance.repository.InsurerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsuranceClaimServiceTest {

    @Mock
    private ClaimRequestRepository claimRequestRepository;

    @Mock
    private InsurerRepository insurerRepository;

    @InjectMocks
    private InsuranceClaimService insuranceClaimService;

    private Insurer sampleInsurer;
    private ClaimRequest sampleClaimRequest;
    private ClaimInitiationRequest sampleInitiationRequest;

    @BeforeEach
    void setUp() {
        sampleInsurer = new Insurer();
        sampleInsurer.setId(1L);
        sampleInsurer.setInsurerName("Health Insurance Corp");
        sampleInsurer.setPackageName("Premium Health Package");
        sampleInsurer.setInsuranceAmountLimit(500000.0);
        sampleInsurer.setDisbursementDurationDays(30);
        sampleInsurer.setContactPhone("123-456-7890");
        sampleInsurer.setAddress("123 Insurance St, City, State");
        sampleInsurer.setActive(true);

        sampleClaimRequest = new ClaimRequest();
        sampleClaimRequest.setId(1L);
        sampleClaimRequest.setPatientName("John Doe");
        sampleClaimRequest.setAilment("Heart Surgery");
        sampleClaimRequest.setTreatmentPackageName("Cardiac Treatment");
        sampleClaimRequest.setTreatmentCost(25000.0);
        sampleClaimRequest.setInsurerName("Health Insurance Corp");
        sampleClaimRequest.setInsurerPackageName("Premium Health Package");
        sampleClaimRequest.setInsuranceAmountLimit(500000.0);
        sampleClaimRequest.setBalanceAmount(0.0);
        sampleClaimRequest.setClaimStatus("INITIATED");
        sampleClaimRequest.setPatientId(123L);

        sampleInitiationRequest = new ClaimInitiationRequest();
        sampleInitiationRequest.setPatientName("John Doe");
        sampleInitiationRequest.setAilment("Heart Surgery");
        sampleInitiationRequest.setTreatmentPackageName("Cardiac Treatment");
        sampleInitiationRequest.setTreatmentCost(25000.0);
        sampleInitiationRequest.setInsurerName("Health Insurance Corp");
        sampleInitiationRequest.setPatientId(123L);
    }

    @Test
    void initiateClaim_ShouldCreateClaimRequest_WhenValidInput() {
        // Given
        when(insurerRepository.findByInsurerName("Health Insurance Corp")).thenReturn(Optional.of(sampleInsurer));
        when(claimRequestRepository.save(any(ClaimRequest.class))).thenReturn(sampleClaimRequest);

        // When
        ClaimInitiationResponse result = insuranceClaimService.initiateClaim(sampleInitiationRequest);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getPatientName());
        assertEquals(25000.0, result.getTreatmentCost());
        assertEquals("INITIATED", result.getClaimStatus());
        verify(claimRequestRepository, times(1)).save(any(ClaimRequest.class));
    }

    @Test
    void initiateClaim_ShouldThrowException_WhenInsurerNotFound() {
        // Given
        when(insurerRepository.findByInsurerName("Health Insurance Corp")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            insuranceClaimService.initiateClaim(sampleInitiationRequest);
        });
        verify(claimRequestRepository, never()).save(any(ClaimRequest.class));
    }

    @Test
    void getAllInsurerDetails_ShouldReturnAllActiveInsurers() {
        // Given
        List<Insurer> insurers = Arrays.asList(sampleInsurer);
        when(insurerRepository.findByActive(true)).thenReturn(insurers);

        // When
        List<InsurerDTO> result = insuranceClaimService.getAllInsurerDetails();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Health Insurance Corp", result.get(0).getInsurerName());
    }

    @Test
    void getInsurerByPackageName_ShouldReturnInsurer_WhenValidPackageName() {
        // Given
        List<Insurer> insurers = Arrays.asList(sampleInsurer);
        when(insurerRepository.findByPackageName("Premium Health Package")).thenReturn(insurers);

        // When
        InsurerDTO result = insuranceClaimService.getInsurerByPackageName("Premium Health Package");

        // Then
        assertNotNull(result);
        assertEquals("Health Insurance Corp", result.getInsurerName());
        assertEquals(500000.0, result.getInsuranceAmountLimit());
    }

    @Test
    void getInsurerByPackageName_ShouldThrowException_WhenPackageNotFound() {
        // Given
        when(insurerRepository.findByPackageName("Non Existent Package")).thenReturn(Arrays.asList());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            insuranceClaimService.getInsurerByPackageName("Non Existent Package");
        });
    }

    @Test
    void getClaimsByStatus_ShouldReturnClaimsWithStatus() {
        // Given
        List<ClaimRequest> claims = Arrays.asList(sampleClaimRequest);
        when(claimRequestRepository.findByClaimStatus("INITIATED")).thenReturn(claims);

        // When
        List<ClaimRequest> result = insuranceClaimService.getClaimsByStatus("initiated");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getPatientName());
    }

    @Test
    void getClaimByPatientId_ShouldReturnClaim_WhenValidPatientId() {
        // Given
        when(claimRequestRepository.findByPatientId(123L)).thenReturn(Optional.of(sampleClaimRequest));

        // When
        ClaimRequest result = insuranceClaimService.getClaimByPatientId(123L);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getPatientName());
        assertEquals("INITIATED", result.getClaimStatus());
    }

    @Test
    void getClaimByReferenceNumber_ShouldReturnClaim_WhenValidReferenceNumber() {
        // Given
        String referenceNumber = "MF-HEA-20241201120000";
        sampleClaimRequest.setClaimReferenceNumber(referenceNumber);
        when(claimRequestRepository.findByClaimReferenceNumber(referenceNumber)).thenReturn(Optional.of(sampleClaimRequest));

        // When
        ClaimRequest result = insuranceClaimService.getClaimByReferenceNumber(referenceNumber);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getPatientName());
        assertEquals(referenceNumber, result.getClaimReferenceNumber());
    }

    @Test
    void updateClaimStatus_ShouldUpdateStatus() {
        // Given
        when(claimRequestRepository.findById(1L)).thenReturn(Optional.of(sampleClaimRequest));
        when(claimRequestRepository.save(any(ClaimRequest.class))).thenReturn(sampleClaimRequest);

        // When
        ClaimRequest result = insuranceClaimService.updateClaimStatus(1L, "approved");

        // Then
        assertNotNull(result);
        verify(claimRequestRepository, times(1)).save(any(ClaimRequest.class));
    }
}