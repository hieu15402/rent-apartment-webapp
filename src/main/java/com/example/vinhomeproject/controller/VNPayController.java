package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.VNPayService;
import jakarta.mail.MessagingException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/vnpay")
public class VNPayController {
    @Autowired
    public VNPayService service;

    @GetMapping("/payment-callback")
    public ModelAndView paymentCallback(@RequestParam Map<String, String> queryParams) throws MessagingException {
        ModelAndView modelAndView = new ModelAndView(service.paymentCallback(queryParams));
        String paymentId = queryParams.get("paymentId");
        modelAndView.addObject("userId",service.getUserByPaymentId(paymentId));
        service.sendMail(Integer.parseInt(String.valueOf(service.getUserByPaymentId(paymentId))),paymentId);
        return modelAndView;
    }
    @GetMapping("/payment")
    public ResponseEntity<ResponseObject> payment(@PathParam("price") long price,
                                                  @PathParam("paymentId") String paymentId,
                                                  @RequestParam("bankCode") String bankCode)
            throws UnsupportedEncodingException {
        return service.payment(price,paymentId,bankCode);
    }
}
