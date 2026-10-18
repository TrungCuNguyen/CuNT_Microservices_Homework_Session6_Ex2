package com.example.patientservice.service.impl;

import com.example.patientservice.constant.PatientConstant;
import com.example.patientservice.dto.request.PatientRequestDTO;
import com.example.patientservice.dto.response.PatientResponseDTO;
import com.example.patientservice.entity.Patient;
import com.example.patientservice.exception.ResourceNotFoundException;
import com.example.patientservice.repository.PatientRepository;
import com.example.patientservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {
        Patient patient = Patient.builder()
                .id(requestDTO.getId())
                .fullName(requestDTO.getFullName())
                .dateOfBirth(requestDTO.getDateOfBirth())
                .gender(requestDTO.getGender())
                .phoneNumber(requestDTO.getPhoneNumber())
                .address(requestDTO.getAddress())
                .medicalHistory(requestDTO.getMedicalHistory())
                .build();

        Patient savedPatient = patientRepository.save(patient);
        return mapToResponse(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PatientConstant.PATIENT_NOT_FOUND, id)));
        return mapToResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PatientConstant.PATIENT_NOT_FOUND, id)));

        patient.setFullName(requestDTO.getFullName());
        patient.setDateOfBirth(requestDTO.getDateOfBirth());
        patient.setGender(requestDTO.getGender());
        patient.setPhoneNumber(requestDTO.getPhoneNumber());
        patient.setAddress(requestDTO.getAddress());
        patient.setMedicalHistory(requestDTO.getMedicalHistory());

        Patient updatedPatient = patientRepository.save(patient);
        return mapToResponse(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PatientConstant.PATIENT_NOT_FOUND, id)));
        patientRepository.delete(patient);
    }

    private PatientResponseDTO mapToResponse(Patient patient) {
        return PatientResponseDTO.builder()
                .id(patient.getId())
                .fullName(patient.getFullName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .address(patient.getAddress())
                .medicalHistory(patient.getMedicalHistory())
                .build();
    }
}
