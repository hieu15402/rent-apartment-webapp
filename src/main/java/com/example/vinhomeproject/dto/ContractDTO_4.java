package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Users;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDTO_4 {
    private Long id;
    private String apartmentName;
    private boolean contractStatus;
    private double totalPrice;
    private String landlordName;
    private String renterName;
    private long durationMonth;
    private String address;
    private String urlFile;
}
