package com.ehr_integration_platform.controller;

import com.ehr_integration_platform.common.ApiResponse;
import com.ehr_integration_platform.dto.AppointmentDTO;
import com.ehr_integration_platform.service.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(
            AppointmentService service
    ) {
        this.service = service;
    }

    // BOOK APPOINTMENT
    @PreAuthorize("hasAnyRole('PATIENT','ADMIN')")
    @PostMapping
    public ApiResponse<AppointmentDTO> bookAppointment(
            @RequestBody AppointmentDTO dto
    ) {

        AppointmentDTO saved =
                service.bookAppointment(dto);

        return new ApiResponse<>(
                "Appointment booked successfully",
                saved
        );
    }

    // GET ALL APPOINTMENTS
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public ApiResponse<List<AppointmentDTO>>
    getAllAppointments() {

        List<AppointmentDTO> list =
                service.getAllAppointments();

        return new ApiResponse<>(
                "Appointments fetched successfully",
                list
        );
    }

    // APPROVE APPOINTMENT
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PutMapping("/{id}/approve")
    public ApiResponse<AppointmentDTO>
    approveAppointment(
            @PathVariable Long id
    ) {

        AppointmentDTO updated =
                service.approveAppointment(id);

        return new ApiResponse<>(
                "Appointment approved successfully",
                updated
        );
    }

    // REJECT APPOINTMENT
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    @PutMapping("/{id}/reject")
    public ApiResponse<AppointmentDTO>
    rejectAppointment(
            @PathVariable Long id
    ) {

        AppointmentDTO updated =
                service.rejectAppointment(id);

        return new ApiResponse<>(
                "Appointment rejected successfully",
                updated
        );
    }
}