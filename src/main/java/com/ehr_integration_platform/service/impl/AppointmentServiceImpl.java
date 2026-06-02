package com.ehr_integration_platform.service.impl;

import com.ehr_integration_platform.dto.AppointmentDTO;
import com.ehr_integration_platform.entity.Appointment;
import com.ehr_integration_platform.entity.AppointmentStatus;
import com.ehr_integration_platform.entity.Doctor;
import com.ehr_integration_platform.entity.Patient;
import com.ehr_integration_platform.exception.ResourceNotFoundException;
import com.ehr_integration_platform.mapper.AppointmentMapper;
import com.ehr_integration_platform.repository.AppointmentRepository;
import com.ehr_integration_platform.repository.DoctorRepository;
import com.ehr_integration_platform.repository.PatientRepository;
import com.ehr_integration_platform.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl
        implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    private final AppointmentMapper mapper;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentMapper mapper
    ) {

        this.appointmentRepository =
                appointmentRepository;

        this.patientRepository =
                patientRepository;

        this.doctorRepository =
                doctorRepository;

        this.mapper = mapper;
    }

    // BOOK APPOINTMENT
    @Override
    public AppointmentDTO bookAppointment(
            AppointmentDTO dto
    ) {

        Patient patient =
                patientRepository.findById(
                                dto.getPatientId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found"
                                ));

        Doctor doctor =
                doctorRepository.findById(
                                dto.getDoctorId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found"
                                ));

        // CONFLICT VALIDATION
        boolean exists =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentTime(
                                dto.getDoctorId(),
                                dto.getAppointmentTime()
                        );

        if (exists) {

            throw new RuntimeException(
                    "Doctor already has appointment at this time"
            );
        }

        Appointment appointment =
                new Appointment();

        appointment.setPatient(patient);

        appointment.setDoctor(doctor);

        appointment.setAppointmentTime(
                dto.getAppointmentTime()
        );

        appointment.setNotes(
                dto.getNotes()
        );

        appointment.setStatus(
                AppointmentStatus.PENDING
        );

        Appointment saved =
                appointmentRepository.save(
                        appointment
                );

        return mapper.toDTO(saved);
    }

    // APPROVE APPOINTMENT
    @Override
    public AppointmentDTO approveAppointment(
            Long id
    ) {

        Appointment appointment =
                appointmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found"
                                ));

        appointment.setStatus(
                AppointmentStatus.APPROVED
        );

        Appointment updated =
                appointmentRepository.save(
                        appointment
                );

        return mapper.toDTO(updated);
    }

    // REJECT APPOINTMENT
    @Override
    public AppointmentDTO rejectAppointment(
            Long id
    ) {

        Appointment appointment =
                appointmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found"
                                ));

        appointment.setStatus(
                AppointmentStatus.REJECTED
        );

        Appointment updated =
                appointmentRepository.save(
                        appointment
                );

        return mapper.toDTO(updated);
    }

    // GET ALL APPOINTMENTS
    @Override
    public List<AppointmentDTO>
    getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}