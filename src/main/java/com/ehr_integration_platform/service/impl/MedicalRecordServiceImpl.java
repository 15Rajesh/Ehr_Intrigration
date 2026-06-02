package com.ehr_integration_platform.service.impl;

import com.ehr_integration_platform.dto.MedicalRecordDTO;
import com.ehr_integration_platform.entity.MedicalRecord;
import com.ehr_integration_platform.entity.Patient;
import com.ehr_integration_platform.exception.ResourceNotFoundException;
import com.ehr_integration_platform.mapper.MedicalRecordMapper;
import com.ehr_integration_platform.repository.MedicalRecordRepository;
import com.ehr_integration_platform.repository.PatientRepository;
import com.ehr_integration_platform.service.MedicalRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl
        implements MedicalRecordService {

    private final MedicalRecordRepository repository;

    private final PatientRepository patientRepository;

    private final MedicalRecordMapper mapper;

    public MedicalRecordServiceImpl(
            MedicalRecordRepository repository,
            PatientRepository patientRepository,
            MedicalRecordMapper mapper
    ) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Override
    public MedicalRecordDTO createRecord(
            MedicalRecordDTO dto
    ) {

        Patient patient =
                patientRepository.findById(
                                dto.getPatientId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found"
                                ));

        MedicalRecord record =
                new MedicalRecord();

        record.setPatient(patient);
        record.setDiagnosis(dto.getDiagnosis());
        record.setTreatment(dto.getTreatment());
        record.setPrescription(dto.getPrescription());
        record.setNotes(dto.getNotes());

        MedicalRecord saved =
                repository.save(record);

        return mapper.toDTO(saved);
    }

    @Override
    public List<MedicalRecordDTO>
    getPatientRecords(Long patientId) {

        return repository.findByPatientId(patientId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}