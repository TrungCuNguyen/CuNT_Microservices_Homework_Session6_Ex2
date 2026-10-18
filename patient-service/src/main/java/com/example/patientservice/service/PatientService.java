package com.example.patientservice.service;

import com.example.patientservice.dto.request.PatientRequestDTO;
import com.example.patientservice.dto.response.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO requestDTO);
    PatientResponseDTO getPatientById(Long id);
    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO updatePatient(Long id, PatientRequestDTO requestDTO);
    void deletePatient(Long id);
}
