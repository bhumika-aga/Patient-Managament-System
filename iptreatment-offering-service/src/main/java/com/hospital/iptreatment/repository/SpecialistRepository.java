package com.hospital.iptreatment.repository;

import com.hospital.iptreatment.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    List<Specialist> findBySpecialization(String specialization);
    
    List<Specialist> findByLevel(String level);
    
    List<Specialist> findBySpecializationAndLevel(String specialization, String level);
    
    List<Specialist> findByAvailable(Boolean available);
}