package com.ehr_integration_platform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String mobileNumber;

    private String otp;

    private LocalDateTime otpExpiry;

    @Enumerated(EnumType.STRING)
    private Role role;
}