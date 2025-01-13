package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.models.Users;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDTO_3 {
    private LocalDate createDateContract;
    private LocalDate expireDateContract;
    private long durationMonth;
    private double totalPrice;
    private double pricePerMonth;
    private String areaName;
    private String zoneName;
    private String buildingName;
    private Apartment apartment;
    private Users landlord;
    private Users renter;
    private BankDTO bank;
    private String urlContract;
    private boolean statusOfPayment;
}
