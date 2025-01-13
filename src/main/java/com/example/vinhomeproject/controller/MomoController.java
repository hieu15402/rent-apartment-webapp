package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.MomoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


public class MomoController {
    private MomoService service;

    @PostMapping("/payment")
    private ResponseEntity<ResponseObject> createPayment(@RequestParam Long paymentId,
                                                         @RequestParam Long price) throws JsonProcessingException {
       return service.createPayment(paymentId,price);
    }
}
