package com.example.vinhomeproject.service;

import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.repositories.PaymentRepository;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.response.momo.MomoCreatePaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MomoService {

    private String partnerCode = "MOMO";
    private String accessKey = "F8BBA842ECF85";
    private String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
    private String returnUrl = "https://localhost:8080/api/v1/momo/call-back/";
    private String notifyUrl = "https://localhost:8080/api/v1/momo/MomoNotify";
    private String momoApiUrl = "https://test-payment.momo.vn/gw_payment/transactionProcessor";
    private String requestType = "captureMoMoWallet";

    @Autowired
    private UsersRepository repository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RestTemplate restTemplate;
    public ResponseEntity<ResponseObject> createPayment(Long paymentId, Long price) throws JsonProcessingException {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if(payment.isPresent()){
            String orderId = String.valueOf(System.currentTimeMillis());

            String rawData = "partnerCode="+partnerCode+"&accessKey="+accessKey+"&requestId="+orderId+"&amount="+price+"&orderId="+orderId+"&orderInfo="+payment.get().getContent()+"&returnUrl="+returnUrl+"&notifyUrl="+notifyUrl+"&extraData=";

            String signature = computeHmacSha256(rawData, secretKey);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestData = new HashMap<>();
            requestData.put("accessKey", accessKey);
            requestData.put("partnerCode", partnerCode);
            requestData.put("requestType", requestType);
            requestData.put("notifyUrl", notifyUrl);
            requestData.put("returnUrl", returnUrl);
            requestData.put("orderId", orderId);
            requestData.put("amount", price.toString());
            requestData.put("orderInfo", payment.get().getContent());
            requestData.put("requestId", orderId);
            requestData.put("extraData", "");
            requestData.put("signature", signature);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestData, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(momoApiUrl, HttpMethod.POST, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Create payment successfully",
                    objectMapper.readValue(responseEntity.getBody(), MomoCreatePaymentResponse.class)
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found payment",
                ""
        ));
    }
       private String computeHmacSha256(String data, String secretKey) {
            try {
                byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
                byte[] messageBytes = data.getBytes(StandardCharsets.UTF_8);

                Mac hmac = Mac.getInstance("HmacSha256");
                SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSha256");
                hmac.init(secretKeySpec);

                byte[] hashBytes = hmac.doFinal(messageBytes);

                StringBuilder sb = new StringBuilder();
                Formatter formatter = new Formatter(sb);
                for (byte b : hashBytes) {
                    formatter.format("%02x", b);
                    }
                return sb.toString();
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
                return null;
            }
       }
}
