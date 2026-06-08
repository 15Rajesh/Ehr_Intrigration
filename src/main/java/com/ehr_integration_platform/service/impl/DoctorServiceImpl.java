package com.ehr_integration_platform.service.impl;

import com.ehr_integration_platform.dto.DoctorDTO;
import com.ehr_integration_platform.entity.Doctor;
import com.ehr_integration_platform.exception.ResourceNotFoundException;
import com.ehr_integration_platform.mapper.DoctorMapper;
import com.ehr_integration_platform.repository.DoctorRepository;
import com.ehr_integration_platform.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {

        Doctor doctor = DoctorMapper.toEntity(doctorDTO);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return DoctorMapper.toDTO(savedDoctor);
    }

    @Override
    public DoctorDTO getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: " + id));

        return DoctorMapper.toDTO(doctor);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: " + id));

        doctor.setName(doctorDTO.getName());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setMobileNumber(doctorDTO.getMobileNumber());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setExperience(doctorDTO.getExperience());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return DoctorMapper.toDTO(updatedDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: " + id));

        doctorRepository.delete(doctor);
    }
}