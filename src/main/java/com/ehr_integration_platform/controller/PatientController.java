package com.ehr_integration_platform.controller;

import com.ehr_integration_platform.common.ApiResponse;
import com.ehr_integration_platform.dto.PatientDTO;
import com.ehr_integration_platform.service.PatientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Slf4j
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // CREATE PATIENT
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping
    public ApiResponse<PatientDTO> createPatient(
            @Valid @RequestBody PatientDTO dto
    ) {

        PatientDTO saved = service.savePatient(dto);

        return new ApiResponse<>(
                "Patient created successfully",
                saved
        );
    }

    // GET ALL PATIENTS
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping
    public ApiResponse<?> getAllPatients(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction,

            @RequestParam(required = false) String name,

            @RequestParam(required = false) String gender,

            @RequestParam(required = false) String disease
    ) {

        Page<PatientDTO> result =
                service.getAllPatients(
                        page,
                        size,
                        sortBy,
                        direction,
                        name,
                        gender,
                        disease
                );

        if (result.isEmpty()) {

            return new ApiResponse<>(
                    "No data found",
                    result.getContent()
            );
        }

        return new ApiResponse<>(
                "Success",
                result
        );
    }

    // GET PATIENT BY ID
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/{id}")
    public ApiResponse<PatientDTO> getPatient(
            @PathVariable Long id
    ) {

        PatientDTO patient = service.getPatientById(id);

        return new ApiResponse<>(
                "Success",
                patient
        );
    }

    // GET BY MOBILE
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mobile/{number}")
    public ApiResponse<PatientDTO> getByMobile(
            @PathVariable String number
    ) {

        PatientDTO patient = service.getByMobile(number);

        return new ApiResponse<>(
                "Success",
                patient
        );
    }

    // SEARCH PATIENT
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/search")
    public ApiResponse<?> searchByName(
            @RequestParam String name
    ) {

        List<PatientDTO> list = service.searchByName(name);

        if (list.isEmpty()) {

            return new ApiResponse<>(
                    "No data found",
                    list
            );
        }

        return new ApiResponse<>(
                "Success",
                list
        );
    }

    // PARTIAL UPDATE
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PatchMapping("/{id}")
    public ApiResponse<PatientDTO> partialUpdate(

            @PathVariable Long id,

            @RequestBody PatientDTO dto
    ) {

        PatientDTO updated =
                service.updatePartial(id, dto);

        return new ApiResponse<>(
                "Patient updated successfully",
                updated
        );
    }

    // FULL UPDATE
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PutMapping("/{id}")
    public ApiResponse<PatientDTO> updatePatient(

            @PathVariable Long id,

            @Valid @RequestBody PatientDTO dto
    ) {

        dto.setId(id);

        PatientDTO updated =
                service.savePatient(dto);

        return new ApiResponse<>(
                "Patient updated successfully",
                updated
        );
    }

    // DELETE PATIENT
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePatient(
            @PathVariable Long id
    ) {

        service.deletePatient(id);

        log.info("Deleted patient with id: {}", id);

        return new ApiResponse<>(
                "Deleted Successfully",
                null
        );
    }

    // CURRENT LOGGED-IN PATIENT DETAILS
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/me")
    public ApiResponse<PatientDTO> getCurrentPatient(
            Authentication authentication
    ) {

        String mobileNumber = authentication.getName();

        PatientDTO patient =
                service.getCurrentPatient(mobileNumber);

        return new ApiResponse<>(
                "Patient profile fetched successfully",
                patient
        );
    }
}