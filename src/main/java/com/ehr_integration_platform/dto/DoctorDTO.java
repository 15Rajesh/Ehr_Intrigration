package com.ehr_integration_platform.dto;

import lombok.Data;

@Data
public class DoctorDTO {

    private Long id;

    private String name;

    private String specialization;

    private String mobileNumber;

    private String email;

    private Integer experience;
}