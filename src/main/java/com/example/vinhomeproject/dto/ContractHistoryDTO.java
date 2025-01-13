package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Contract;
import com.example.vinhomeproject.models.Users;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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

}
