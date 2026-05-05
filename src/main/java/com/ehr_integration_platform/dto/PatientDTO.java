package com.ehr_integration_platform.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PatientDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    @NotBlank(message = "Disease is required")
    private String disease;

    // 🔥 Updated Gender Validation (case-insensitive)
    @Pattern(regexp = "^(?i)(Male|Female|Other)$", message = "Invalid gender")
    private String gender;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    private String mobileNumber;

    private String fatherName;
    private String motherName;
    private String doctorName;
    private String wifeName;
    private String husbandName;


}