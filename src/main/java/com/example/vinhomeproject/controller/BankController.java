package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/bank")
public class BankController {
    @Autowired
    private BankService service;
    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return service.getAll();
    }
}
