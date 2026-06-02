package com.ehr_integration_platform.repository;

import com.ehr_integration_platform.entity.Appointment;
import com.ehr_integration_platform.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    // conflict checking
    Optional<Appointment> findByDoctorAndAppointmentTime(
            Doctor doctor,
            LocalDateTime appointmentTime
    );

    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);
}