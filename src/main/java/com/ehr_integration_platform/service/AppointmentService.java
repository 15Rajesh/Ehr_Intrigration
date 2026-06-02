package com.ehr_integration_platform.service;

import com.ehr_integration_platform.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {

    // Book appointment
    AppointmentDTO bookAppointment(
            AppointmentDTO dto
    );

    // Approve appointment
    AppointmentDTO approveAppointment(
            Long id
    );

    // Reject appointment
    AppointmentDTO rejectAppointment(
            Long id
    );

    // Get all appointments
    List<AppointmentDTO> getAllAppointments();
}