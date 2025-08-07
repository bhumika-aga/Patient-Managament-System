package com.hospital.insurance.repository;

import com.hospital.insurance.entity.ClaimRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClaimRequestRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClaimRequestRepository claimRequestRepository;

    private ClaimRequest sampleClaim1;
    private ClaimRequest sampleClaim2;

    @BeforeEach
    void setUp() {
        sampleClaim1 = new ClaimRequest();
        sampleClaim1.setPatientName("John Doe");
        sampleClaim1.setAilment("Heart Surgery");
        sampleClaim1.setTreatmentPackageName("Cardiac Treatment");
        sampleClaim1.setTreatmentCost(25000.0);
        sampleClaim1.setInsurerName("Health Insurance Corp");
        sampleClaim1.setInsurerPackageName("Premium Health Package");
        sampleClaim1.setInsuranceAmountLimit(500000.0);
        sampleClaim1.setBalanceAmount(0.0);
        sampleClaim1.setClaimStatus("INITIATED");
        sampleClaim1.setClaimInitiatedDate(LocalDateTime.now());
        sampleClaim1.setPatientId(123L);

        sampleClaim2 = new ClaimRequest();
        sampleClaim2.setPatientName("Jane Smith");
        sampleClaim2.setAilment("Knee Surgery");
        sampleClaim2.setTreatmentPackageName("Orthopedic Treatment");
        sampleClaim2.setTreatmentCost(30000.0);
        sampleClaim2.setInsurerName("Life Insurance Ltd");
        sampleClaim2.setInsurerPackageName("Basic Health Package");
        sampleClaim2.setInsuranceAmountLimit(300000.0);
        sampleClaim2.setBalanceAmount(0.0);
        sampleClaim2.setClaimStatus("APPROVED");
        sampleClaim2.setClaimInitiatedDate(LocalDateTime.now());
        sampleClaim2.setPatientId(456L);

        entityManager.persistAndFlush(sampleClaim1);
        entityManager.persistAndFlush(sampleClaim2);
    }

    @Test
    void findByPatientName_ShouldReturnClaimsForPatient() {
        // When
        List<ClaimRequest> claims = claimRequestRepository.findByPatientName("John Doe");

        // Then
        assertNotNull(claims);
        assertEquals(1, claims.size());
        assertEquals("John Doe", claims.get(0).getPatientName());
        assertEquals("INITIATED", claims.get(0).getClaimStatus());
    }

    @Test
    void findByPatientName_ShouldReturnEmptyList_WhenPatientNotFound() {
        // When
        List<ClaimRequest> claims = claimRequestRepository.findByPatientName("Non Existent");

        // Then
        assertNotNull(claims);
        assertTrue(claims.isEmpty());
    }

    @Test
    void findByClaimStatus_ShouldReturnClaimsWithStatus() {
        // When
        List<ClaimRequest> initiatedClaims = claimRequestRepository.findByClaimStatus("INITIATED");
        List<ClaimRequest> approvedClaims = claimRequestRepository.findByClaimStatus("APPROVED");

        // Then
        assertEquals(1, initiatedClaims.size());
        assertEquals("John Doe", initiatedClaims.get(0).getPatientName());
        
        assertEquals(1, approvedClaims.size());
        assertEquals("Jane Smith", approvedClaims.get(0).getPatientName());
    }

    @Test
    void findByInsurerName_ShouldReturnClaimsForInsurer() {
        // When
        List<ClaimRequest> claims = claimRequestRepository.findByInsurerName("Health Insurance Corp");

        // Then
        assertNotNull(claims);
        assertEquals(1, claims.size());
        assertEquals("John Doe", claims.get(0).getPatientName());
    }

    @Test
    void save_ShouldPersistClaimRequest() {
        // Given
        ClaimRequest newClaim = new ClaimRequest();
        newClaim.setPatientName("Bob Johnson");
        newClaim.setAilment("Eye Surgery");
        newClaim.setTreatmentPackageName("Vision Care");
        newClaim.setTreatmentCost(20000.0);
        newClaim.setInsurerName("Vision Insurance Co");
        newClaim.setInsurerPackageName("Eye Care Package");
        newClaim.setInsuranceAmountLimit(200000.0);
        newClaim.setBalanceAmount(0.0);
        newClaim.setClaimStatus("INITIATED");
        newClaim.setClaimInitiatedDate(LocalDateTime.now());
        newClaim.setPatientId(789L);

        // When
        ClaimRequest savedClaim = claimRequestRepository.save(newClaim);

        // Then
        assertNotNull(savedClaim.getId());
        assertEquals("Bob Johnson", savedClaim.getPatientName());
        assertEquals(20000.0, savedClaim.getTreatmentCost());
    }

    @Test
    void findByPatientId_ShouldReturnClaimForPatient() {
        // When
        Optional<ClaimRequest> claim = claimRequestRepository.findByPatientId(123L);

        // Then
        assertTrue(claim.isPresent());
        assertEquals("John Doe", claim.get().getPatientName());
    }

    @Test
    void findByClaimReferenceNumber_ShouldReturnClaimWhenFound() {
        // Given
        String referenceNumber = "MF-HEA-20241201120000";
        sampleClaim1.setClaimReferenceNumber(referenceNumber);
        entityManager.persistAndFlush(sampleClaim1);

        // When
        Optional<ClaimRequest> claim = claimRequestRepository.findByClaimReferenceNumber(referenceNumber);

        // Then
        assertTrue(claim.isPresent());
        assertEquals(referenceNumber, claim.get().getClaimReferenceNumber());
    }
}