package com.example.vinhomeproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenuePerMonthDTO {
    private int month;
    private double revenue;
}
