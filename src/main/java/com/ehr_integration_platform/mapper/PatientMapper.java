package com.ehr_integration_platform.mapper;

import com.ehr_integration_platform.dto.PatientDTO;
import com.ehr_integration_platform.entity.Patient;

public class PatientMapper {

    // DTO → Entity
    public static Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;

        Patient p = new Patient();

        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setAge(dto.getAge());
        p.setDisease(dto.getDisease());
        p.setGender(dto.getGender());
        p.setMobileNumber(dto.getMobileNumber());
        p.setFatherName(dto.getFatherName());
        p.setMotherName(dto.getMotherName());
        p.setDoctorName(dto.getDoctorName());
        p.setWifeName(dto.getWifeName());
        p.setHusbandName(dto.getHusbandName());

        return p;
    }

    // Entity → DTO
    public static PatientDTO toDTO(Patient p) {
        if (p == null) return null;

        PatientDTO dto = new PatientDTO();

        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setAge(p.getAge());
        dto.setDisease(p.getDisease());
        dto.setGender(p.getGender());
        dto.setMobileNumber(p.getMobileNumber());
        dto.setFatherName(p.getFatherName());
        dto.setMotherName(p.getMotherName());
        dto.setDoctorName(p.getDoctorName());
        dto.setWifeName(p.getWifeName());
        dto.setHusbandName(p.getHusbandName());

        return dto;
    }
}