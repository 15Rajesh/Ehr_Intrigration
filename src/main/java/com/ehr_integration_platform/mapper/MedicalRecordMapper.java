package com.ehr_integration_platform.mapper;

import com.ehr_integration_platform.dto.MedicalRecordDTO;
import com.ehr_integration_platform.entity.MedicalRecord;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {

    public MedicalRecordDTO toDTO(
            MedicalRecord record
    ) {

        MedicalRecordDTO dto =
                new MedicalRecordDTO();

        dto.setId(record.getId());

        dto.setPatientId(
                record.getPatient().getId()
        );

        dto.setDiagnosis(
                record.getDiagnosis()
        );

        dto.setTreatment(
                record.getTreatment()
        );

        dto.setPrescription(
                record.getPrescription()
        );

        dto.setNotes(
                record.getNotes()
        );

        return dto;
    }
}