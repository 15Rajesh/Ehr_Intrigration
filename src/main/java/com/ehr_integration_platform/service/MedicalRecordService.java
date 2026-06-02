package com.ehr_integration_platform.service;

import com.ehr_integration_platform.dto.MedicalRecordDTO;

import java.util.List;

public interface MedicalRecordService {

    MedicalRecordDTO createRecord(
            MedicalRecordDTO dto
    );

    List<MedicalRecordDTO> getPatientRecords(
            Long patientId
    );
}