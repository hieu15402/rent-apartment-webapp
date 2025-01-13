package com.example.vinhomeproject.response.momo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    public String fullName ;
    public String orderId ;
    public String orderInfo ;
    public double amount ;
}
