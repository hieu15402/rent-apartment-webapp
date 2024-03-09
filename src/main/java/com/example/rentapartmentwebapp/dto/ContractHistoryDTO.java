package com.example.rentapartmentwebapp.dto;

import com.example.rentapartmentwebapp.models.Contract;
import com.example.rentapartmentwebapp.models.Users;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractHistoryDTO {
    private double price;
    private String description;
    private String image;
    private LocalDate expiredTime;

    private Users users;

    private Set<Contract> contracts;
}
