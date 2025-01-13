package com.example.vinhomeproject.request;

import com.example.vinhomeproject.models.paypal.PaypalPaymentIntent;
import com.example.vinhomeproject.models.paypal.PaypalPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaypalRequest {
    private double price;
    PaypalPaymentMethod method;
    PaypalPaymentIntent intent;
    String description;
}
