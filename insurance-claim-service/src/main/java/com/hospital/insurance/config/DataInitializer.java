package com.hospital.insurance.config;

import com.hospital.insurance.entity.Insurer;
import com.hospital.insurance.repository.InsurerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {
    
    @Autowired
    private InsurerRepository insurerRepository;
    
    @PostConstruct
    public void initializeData() {
        initializeInsurers();
    }
    
    private void initializeInsurers() {
        if (insurerRepository.count() == 0) {
            List<Insurer> insurers = Arrays.asList(
                // Orthopaedics Insurance Plans
                createInsurer("Apollo Munich Health Insurance", "Orthopaedics Basic", 2000.0, 7,
                    "claims@apollomunich.com", "+91-1800-116-969", "Mumbai, Maharashtra", "www.apollomunichinsurance.com"),
                
                createInsurer("Star Health Insurance", "Orthopaedics Premium", 3500.0, 10,
                    "claims@starhealth.in", "+91-1800-425-2255", "Chennai, Tamil Nadu", "www.starhealth.in"),
                
                createInsurer("HDFC ERGO Health", "Ortho Care Plus", 2800.0, 5,
                    "claims@hdfcergo.com", "+91-1800-266-9966", "Mumbai, Maharashtra", "www.hdfcergo.com"),
                
                // Urology Insurance Plans
                createInsurer("ICICI Lombard Health", "Urology Specialty", 4500.0, 7,
                    "claims@icicilombard.com", "+91-1800-266-7766", "Mumbai, Maharashtra", "www.icicilombard.com"),
                
                createInsurer("Max Bupa Health", "Urology Complete Care", 5500.0, 12,
                    "claims@maxbupa.com", "+91-1800-103-3911", "New Delhi, Delhi", "www.maxbupa.com"),
                
                createInsurer("Bajaj Allianz Health", "Urology Advanced", 4200.0, 8,
                    "claims@bajajallianz.co.in", "+91-1800-209-0144", "Pune, Maharashtra", "www.bajajallianz.co.in"),
                
                // General Medical Insurance (covers both)
                createInsurer("LIC Health Insurance", "Medical Comprehensive", 6000.0, 15,
                    "claims@licindia.com", "+91-022-6827-6827", "Mumbai, Maharashtra", "www.licindia.in"),
                
                createInsurer("Oriental Insurance", "Family Health Plus", 3000.0, 10,
                    "claims@orientalinsurance.co.in", "+91-1800-118-485", "New Delhi, Delhi", "www.orientalinsurance.co.in"),
                
                createInsurer("National Insurance", "Mediclaim Premium", 4000.0, 12,
                    "claims@nationalinsurance.nic.co.in", "+91-1800-200-7710", "Kolkata, West Bengal", "www.nationalinsuranceindia.nic.co.in"),
                
                createInsurer("United India Insurance", "Health Guardian", 3200.0, 9,
                    "claims@uiic.co.in", "+91-044-2829-2929", "Chennai, Tamil Nadu", "www.uiic.co.in")
            );
            
            insurerRepository.saveAll(insurers);
        }
    }
    
    private Insurer createInsurer(String insurerName, String packageName, Double amountLimit, Integer disbursementDays,
                                  String email, String phone, String address, String website) {
        Insurer insurer = new Insurer(insurerName, packageName, amountLimit, disbursementDays);
        insurer.setContactEmail(email);
        insurer.setContactPhone(phone);
        insurer.setAddress(address);
        insurer.setWebsite(website);
        insurer.setActive(true);
        return insurer;
    }
}