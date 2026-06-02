package com.ehr_integration_platform.dto;

import lombok.Data;

@Data
public class MedicalRecordDTO {

    private Long id;

    private Long patientId;

    private String diagnosis;

    private String treatment;

    private String prescription;

    private String notes;
}