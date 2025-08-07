package com.hospital.iptreatment.config;

import com.hospital.iptreatment.entity.Specialist;
import com.hospital.iptreatment.entity.TreatmentPackage;
import com.hospital.iptreatment.repository.SpecialistRepository;
import com.hospital.iptreatment.repository.TreatmentPackageRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private TreatmentPackageRepository packageRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @PostConstruct
    public void initializeData() {
        initializeTreatmentPackages();
        initializeSpecialists();
    }

    private void initializeTreatmentPackages() {
        if (packageRepository.count() == 0) {
            // Orthopaedics Packages
            TreatmentPackage orthoPkg1 = new TreatmentPackage(
                    "Orthopaedics Package 1",
                    "Orthopaedics",
                    Arrays.asList("OPT1", "OPT2"),
                    2500.0,
                    4,
                    1
            );

            TreatmentPackage orthoPkg2 = new TreatmentPackage(
                    "Orthopaedics Package 2",
                    "Orthopaedics",
                    Arrays.asList("OPT3", "OPT4"),
                    3000.0,
                    6,
                    2
            );

            // Urology Packages
            TreatmentPackage urologyPkg1 = new TreatmentPackage(
                    "Urology Package 1",
                    "Urology",
                    Arrays.asList("UPT1", "UPT2"),
                    4000.0,
                    4,
                    1
            );

            TreatmentPackage urologyPkg2 = new TreatmentPackage(
                    "Urology Package 2",
                    "Urology",
                    Arrays.asList("UPT3", "UPT4"),
                    5000.0,
                    6,
                    2
            );

            packageRepository.saveAll(Arrays.asList(orthoPkg1, orthoPkg2, urologyPkg1, urologyPkg2));
        }
    }

    private void initializeSpecialists() {
        if (specialistRepository.count() == 0) {
            List<Specialist> specialists = Arrays.asList(
                    // Orthopaedics Specialists - Junior
                    new Specialist("Dr. Rajesh Kumar", "Orthopaedics", "JUNIOR", "MBBS, MS Orthopaedics", 3, "+91-9876543210", "rajesh.kumar@mediflow.com"),
                    new Specialist("Dr. Priya Sharma", "Orthopaedics", "JUNIOR", "MBBS, DNB Orthopaedics", 4, "+91-9876543211", "priya.sharma@mediflow.com"),
                    
                    // Orthopaedics Specialists - Senior
                    new Specialist("Dr. Anil Gupta", "Orthopaedics", "SENIOR", "MBBS, MS, MCh Orthopaedics", 15, "+91-9876543212", "anil.gupta@mediflow.com"),
                    new Specialist("Dr. Sunita Rao", "Orthopaedics", "SENIOR", "MBBS, MS, Fellowship Joint Replacement", 18, "+91-9876543213", "sunita.rao@mediflow.com"),
                    
                    // Urology Specialists - Junior
                    new Specialist("Dr. Vikram Singh", "Urology", "JUNIOR", "MBBS, MS Urology", 2, "+91-9876543214", "vikram.singh@mediflow.com"),
                    new Specialist("Dr. Neha Jain", "Urology", "JUNIOR", "MBBS, DNB Urology", 3, "+91-9876543215", "neha.jain@mediflow.com"),
                    
                    // Urology Specialists - Senior
                    new Specialist("Dr. Ashok Mehta", "Urology", "SENIOR", "MBBS, MS, MCh Urology", 20, "+91-9876543216", "ashok.mehta@mediflow.com"),
                    new Specialist("Dr. Kavita Verma", "Urology", "SENIOR", "MBBS, MS, Fellowship Uro-Oncology", 16, "+91-9876543217", "kavita.verma@mediflow.com")
            );

            specialistRepository.saveAll(specialists);
        }
    }
}