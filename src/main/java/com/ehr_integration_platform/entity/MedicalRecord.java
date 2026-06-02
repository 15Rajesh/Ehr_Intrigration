package com.ehr_integration_platform.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medical_records")
@Data
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;

    private String treatment;

    private String prescription;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}