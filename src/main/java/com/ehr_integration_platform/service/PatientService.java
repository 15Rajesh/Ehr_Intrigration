package com.ehr_integration_platform.service;
import com.ehr_integration_platform.mapper.PatientMapper;
import com.ehr_integration_platform.dto.PatientDTO;
import com.ehr_integration_platform.entity.Patient;
import com.ehr_integration_platform.exception.ResourceNotFoundException;
import com.ehr_integration_platform.mapper.PatientMapper;
import com.ehr_integration_platform.repository.PatientRepository;
import com.ehr_integration_platform.specification.PatientSpecification;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientMapper mapper;

    private final PatientRepository repository;

    public PatientService(
            PatientRepository repository,
            PatientMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // CREATE + UPDATE
    public PatientDTO savePatient(PatientDTO dto) {

        Optional<Patient> existing = repository.findByMobileNumber(dto.getMobileNumber());

        if (existing.isPresent() &&
                (dto.getId() == null || !existing.get().getId().equals(dto.getId()))) {
            throw new IllegalArgumentException("Mobile number already exists: " + dto.getMobileNumber());
        }

        Patient patient = PatientMapper.toEntity(dto);
        Patient saved = repository.save(patient);

        return PatientMapper.toDTO(saved);
    }

    //  PAGINATION + FILTER (SPECIFICATION BASED)
    public Page<PatientDTO> getAllPatients(
            int page,
            int size,
            String sortBy,
            String direction,
            String name,
            String gender,
            String disease
    ) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Patient> spec = (root, query, cb) -> cb.conjunction();

        if (name != null && !name.isBlank()) {
            spec = spec.and(PatientSpecification.hasName(name));
        }

        if (gender != null && !gender.isBlank()) {
            spec = spec.and(PatientSpecification.hasGender(gender));
        }

        if (disease != null && !disease.isBlank()) {
            spec = spec.and(PatientSpecification.hasDisease(disease));
        }

        Page<Patient> pageResult = repository.findAll(spec, pageable);

        return pageResult.map(PatientMapper::toDTO);
    }

    // GET BY ID
    public PatientDTO getPatientById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        return PatientMapper.toDTO(patient);
    }

    // GET BY MOBILE
    public PatientDTO getByMobile(String mobile) {
        Patient patient = repository.findByMobileNumber(mobile)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with mobile: " + mobile));

        return PatientMapper.toDTO(patient);
    }

    // SEARCH (optional simple)
    public List<PatientDTO> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(PatientMapper::toDTO)
                .toList();
    }

    public PatientDTO getCurrentPatient(String mobileNumber) {

        Patient patient = repository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found")
                );

        return mapper.toDTO(patient);
    }

    // PATCH (Partial Update)
    public PatientDTO updatePartial(Long id, PatientDTO dto) {

        Patient patient = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        // Mobile duplicate check
        if (dto.getMobileNumber() != null) {
            Optional<Patient> existing = repository.findByMobileNumber(dto.getMobileNumber());

            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new RuntimeException("Mobile number already exists: " + dto.getMobileNumber());
            }
            patient.setMobileNumber(dto.getMobileNumber());
        }

        // Update only non-null fields
        if (dto.getName() != null) patient.setName(dto.getName());
        if (dto.getAge() != null) patient.setAge(dto.getAge());
        if (dto.getDisease() != null) patient.setDisease(dto.getDisease());
        if (dto.getGender() != null) patient.setGender(dto.getGender());
        if (dto.getFatherName() != null) patient.setFatherName(dto.getFatherName());
        if (dto.getMotherName() != null) patient.setMotherName(dto.getMotherName());
        if (dto.getDoctorName() != null) patient.setDoctorName(dto.getDoctorName());
        if (dto.getWifeName() != null) patient.setWifeName(dto.getWifeName());
        if (dto.getHusbandName() != null) patient.setHusbandName(dto.getHusbandName());

        Patient updated = repository.save(patient);

        return PatientMapper.toDTO(updated);
    }

    //  DELETE
    public void deletePatient(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        repository.deleteById(id);
    }
}