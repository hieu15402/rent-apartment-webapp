package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Contract;
import com.example.vinhomeproject.models.ContractHistory;
import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.models.Problems;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDTO_2 {
    private Long id;
    private LocalDate dateSign;
    private String description;
    private LocalDate dateStartRent;
    private ContractHistory contractHistory;
    private Long apartmentId;
    private String apartmentName;
    private String buildingName;
    private String zoneName;
    private String areaName;
    private String urlFile;
    private Long landLordId;
    private boolean statusOfPayment;
}
