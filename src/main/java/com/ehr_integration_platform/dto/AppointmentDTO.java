package com.ehr_integration_platform.dto;

import com.ehr_integration_platform.entity.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private Long id;

    private Long patientId;

    private Long doctorId;

    private LocalDateTime appointmentTime;

    private AppointmentStatus status;

    private String notes;
}