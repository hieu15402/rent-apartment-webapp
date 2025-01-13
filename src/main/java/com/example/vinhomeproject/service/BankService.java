package com.example.vinhomeproject.service;

import com.example.vinhomeproject.models.Bank;
import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.repositories.BankRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    public ResponseEntity<ResponseObject> getAll(){
        List<Bank> banks = repository.findAll();
        if (!banks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get all bank successfully",
                    banks
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "List bank null",
                    ""
            ));
        }
    }

}
