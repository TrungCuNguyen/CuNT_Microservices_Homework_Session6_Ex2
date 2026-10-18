package com.example.patientservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequestDTO {

    private Long id;

    @NotBlank(message = "Họ và tên bệnh nhân không được để trống")
    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String gender;

    private String phoneNumber;

    private String address;

    private String medicalHistory;
}
