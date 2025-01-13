package com.example.vinhomeproject.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO_2 {
    private Long paymentId;
    private double price;
    private LocalDate expiredDate;
    private int semester;
    private Long contractId;
    private boolean status;
}
