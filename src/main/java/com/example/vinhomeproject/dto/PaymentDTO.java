package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Contract;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private LocalDate payment_time;

    private String content;

    private double total_price;
}
