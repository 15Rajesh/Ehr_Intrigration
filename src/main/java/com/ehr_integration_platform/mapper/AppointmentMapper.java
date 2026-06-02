package com.ehr_integration_platform.mapper;

import com.ehr_integration_platform.dto.AppointmentDTO;
import com.ehr_integration_platform.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDTO toDTO(Appointment appointment) {

        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(appointment.getId());

        dto.setPatientId(
                appointment.getPatient().getId()
        );

        dto.setDoctorId(
                appointment.getDoctor().getId()
        );

        dto.setAppointmentTime(
                appointment.getAppointmentTime()
        );

        dto.setStatus(
                appointment.getStatus()
        );

        dto.setNotes(
                appointment.getNotes()
        );

        return dto;
    }
}