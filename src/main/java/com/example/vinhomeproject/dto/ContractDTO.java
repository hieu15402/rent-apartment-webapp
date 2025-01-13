package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.ContractHistory;
import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.models.Problems;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDTO {
    private LocalDate dateSign;
    private String description;
    private LocalDate dateStartRent;
    private ContractHistory contractHistory;
    private Long appointmentId;
}
