package com.ehr_integration_platform.controller;

import com.ehr_integration_platform.common.ApiResponse;
import com.ehr_integration_platform.dto.MedicalRecordDTO;
import com.ehr_integration_platform.service.MedicalRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(
            MedicalRecordService service
    ) {
        this.service = service;
    }

    // CREATE MEDICAL RECORD
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping
    public ApiResponse<MedicalRecordDTO> createRecord(
            @RequestBody MedicalRecordDTO dto
    ) {

        MedicalRecordDTO saved =
                service.createRecord(dto);

        return new ApiResponse<>(
                "Medical record created successfully",
                saved
        );
    }

    // GET PATIENT RECORDS
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT','ADMIN')")
    @GetMapping("/patient/{patientId}")
    public ApiResponse<List<MedicalRecordDTO>>
    getPatientRecords(
            @PathVariable Long patientId
    ) {

        List<MedicalRecordDTO> records =
                service.getPatientRecords(patientId);

        return new ApiResponse<>(
                "Medical records fetched successfully",
                records
        );
    }
}