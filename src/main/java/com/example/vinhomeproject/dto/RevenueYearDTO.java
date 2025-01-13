package com.example.vinhomeproject.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueYearDTO {
    private List<RevenuePerMonthDTO> currentYear;
    private List<RevenuePerMonthDTO> compareYear;
}
