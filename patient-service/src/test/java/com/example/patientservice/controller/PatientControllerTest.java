package com.example.patientservice.controller;

import com.example.patientservice.dto.request.PatientRequestDTO;
import com.example.patientservice.dto.response.PatientResponseDTO;
import com.example.patientservice.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @Test
    void testCreatePatient_Success() throws Exception {
        PatientRequestDTO requestDTO = PatientRequestDTO.builder()
                .fullName("Nguyen Van A")
                .dateOfBirth(LocalDate.of(1995, 5, 20))
                .gender("Nam")
                .phoneNumber("0912345678")
                .address("Ha Noi")
                .medicalHistory("Khong co tien su benh ly")
                .build();

        PatientResponseDTO responseDTO = PatientResponseDTO.builder()
                .id(1L)
                .fullName("Nguyen Van A")
                .dateOfBirth(LocalDate.of(1995, 5, 20))
                .gender("Nam")
                .phoneNumber("0912345678")
                .address("Ha Noi")
                .medicalHistory("Khong co tien su benh ly")
                .build();

        when(patientService.createPatient(any(PatientRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"))
                .andExpect(jsonPath("$.address").value("Ha Noi"))
                .andExpect(jsonPath("$.medicalHistory").value("Khong co tien su benh ly"));
    }

    @Test
    void testGetAllPatients_Success() throws Exception {
        when(patientService.getAllPatients()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
