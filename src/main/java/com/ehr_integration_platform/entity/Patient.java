package com.ehr_integration_platform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "patients")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String doctorName;
    private String name;
    private Integer age;
    private String disease;
    private String gender;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private String fatherName;
    private String motherName;
    private String wifeName;
    private String husbandName;


}