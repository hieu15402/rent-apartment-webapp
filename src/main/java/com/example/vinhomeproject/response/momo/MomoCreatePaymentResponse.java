package com.example.vinhomeproject.response.momo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MomoCreatePaymentResponse {
    public String requestId ;
    public int errorCode ;
    public String orderId ;
    public String message ;
    public String localMessage ;
    public String requestType ;
    public String payUrl ;
    public String signature ;
    public String qrCodeUrl ;
    public String deeplink ;
    public String deeplinkWebInApp ;
}
