package com.example.rentapartmentwebapp.dto;

import com.example.rentapartmentwebapp.models.ContractHistory;
import com.example.rentapartmentwebapp.models.Payment;
import com.example.rentapartmentwebapp.models.Problems;
import lombok.*;

import java.time.LocalDate;
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

    private Set<Problems> problems;

    private Set<Payment> payments;

    private ContractHistory contractHistory;
}
