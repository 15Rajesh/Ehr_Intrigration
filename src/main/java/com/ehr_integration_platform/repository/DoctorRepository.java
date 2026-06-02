package com.ehr_integration_platform.repository;

import com.ehr_integration_platform.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository
        extends JpaRepository<Doctor, Long> {
}