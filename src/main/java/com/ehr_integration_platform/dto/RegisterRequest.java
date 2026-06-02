package com.ehr_integration_platform.dto;

import com.ehr_integration_platform.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;

    private String password;

    private String mobileNumber;

    private Role role;
}
