package com.ehr_integration_platform.controller;

import com.ehr_integration_platform.common.ApiResponse;
import com.ehr_integration_platform.dto.DoctorDTO;
import com.ehr_integration_platform.service.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<DoctorDTO> createDoctor(
            @RequestBody DoctorDTO dto) {

        return new ApiResponse<>(
                "Doctor created successfully",
                service.createDoctor(dto)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public ApiResponse<List<DoctorDTO>> getAllDoctors() {

        return new ApiResponse<>(
                "Success",
                service.getAllDoctors()
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/{id}")
    public ApiResponse<DoctorDTO> getDoctorById(
            @PathVariable Long id) {

        return new ApiResponse<>(
                "Success",
                service.getDoctorById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<DoctorDTO> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorDTO dto) {

        return new ApiResponse<>(
                "Doctor updated successfully",
                service.updateDoctor(id, dto)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDoctor(
            @PathVariable Long id) {

        service.deleteDoctor(id);

        return new ApiResponse<>(
                "Doctor deleted successfully",
                null
        );
    }
}