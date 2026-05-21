package com.ehr_integration_platform.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String mobileNumber;

    private String otp;

    private String newPassword;
}