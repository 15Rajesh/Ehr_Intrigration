package com.ehr_integration_platform.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {

    private String mobileNumber;

    private String otp;
}