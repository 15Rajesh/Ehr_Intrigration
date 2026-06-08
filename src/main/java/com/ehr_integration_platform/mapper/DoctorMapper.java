package com.ehr_integration_platform.mapper;

import com.ehr_integration_platform.dto.DoctorDTO;
import com.ehr_integration_platform.entity.Doctor;

public class DoctorMapper {

    public static DoctorDTO toDTO(Doctor doctor) {

        DoctorDTO dto = new DoctorDTO();

        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setMobileNumber(doctor.getMobileNumber());
        dto.setEmail(doctor.getEmail());
        dto.setExperience(doctor.getExperience());

        return dto;
    }

    public static Doctor toEntity(DoctorDTO dto) {

        Doctor doctor = new Doctor();

        doctor.setId(dto.getId());
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setMobileNumber(dto.getMobileNumber());
        doctor.setEmail(dto.getEmail());
        doctor.setExperience(dto.getExperience());

        return doctor;
    }
}