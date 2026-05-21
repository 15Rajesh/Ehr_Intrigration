package com.ehr_integration_platform.security.controller;

import com.ehr_integration_platform.dto.ResetPasswordRequest;
import com.ehr_integration_platform.dto.SendOtpRequest;
import com.ehr_integration_platform.dto.VerifyOtpRequest;
import com.ehr_integration_platform.entity.Role;
import com.ehr_integration_platform.entity.User;
import com.ehr_integration_platform.repository.UserRepository;
import com.ehr_integration_platform.security.JwtUtil;
import com.ehr_integration_platform.dto.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            JwtUtil jwtUtil,
            AuthenticationManager authManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setMobileNumber(request.getMobileNumber());

        // default role if null
        user.setRole(
                request.getRole() != null
                        ? request.getRole()
                        : Role.PATIENT
        );

        userRepository.save(user);

        return "User registered successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return jwtUtil.generateToken(request.getUsername());
    }
     // Send OTP
    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody SendOtpRequest request) {

        User user = userRepository
                .findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() -> new RuntimeException("Mobile number not found"));

        // generate 6 digit OTP
        String otp = String.valueOf(
                100000 + new Random().nextInt(900000)
        );

        // save OTP
        user.setOtp(otp);

        // expiry = 5 minutes
        user.setOtpExpiry(
                LocalDateTime.now().plusMinutes(5)
        );

        userRepository.save(user);

        // FOR DEVELOPMENT ONLY
        System.out.println("OTP is: " + otp);

        return "OTP sent successfully";
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody VerifyOtpRequest request) {

        User user = userRepository
                .findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // OTP check
        if (!user.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        // expiry check
        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        // clear OTP after successful verification
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        // generate JWT token
        return jwtUtil.generateToken(user.getUsername());
    }

       // Reset Password
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {

        User user = userRepository
                .findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // OTP validation
        if (!user.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        // Expiry validation
        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        // Update password
        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        // Clear OTP
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return "Password reset successful";
    }
}