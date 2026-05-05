package com.ehr_integration_platform.repository;

import com.ehr_integration_platform.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long>,  JpaSpecificationExecutor<Patient>{

    Optional<Patient> findByMobileNumber(String mobileNumber);

    List<Patient> findByNameContainingIgnoreCase(String name);

    // Multi-filter
    List<Patient> findByNameContainingIgnoreCaseAndGenderIgnoreCaseAndDiseaseContainingIgnoreCase(
            String name,
            String gender,
            String disease
    );
}