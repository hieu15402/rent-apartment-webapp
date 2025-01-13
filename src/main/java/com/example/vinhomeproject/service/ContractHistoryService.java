package com.example.vinhomeproject.service;


import com.example.vinhomeproject.dto.ContractDTO;
import com.example.vinhomeproject.dto.ContractHistoryDTO;
import com.example.vinhomeproject.models.Contract;
import com.example.vinhomeproject.models.ContractHistory;
import com.example.vinhomeproject.models.ProblemImage;
import com.example.vinhomeproject.models.Problems;
import com.example.vinhomeproject.repositories.ContractHistoryRepository;
import com.example.vinhomeproject.repositories.ContractRepository;
import com.example.vinhomeproject.repositories.UsersRepository;
import com.example.vinhomeproject.response.ResponseObject;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContractHistoryService {
    @Autowired
    private ContractHistoryRepository contractHistoryRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private UsersRepository usersRepository;
    public ResponseEntity<ResponseObject> getAll(){
        List<ContractHistory> contractHistories = contractHistoryRepository.findAll();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                contractHistories
        ));
    }

    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<ContractHistory> contractHistory = contractHistoryRepository.findById(id);
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                contractHistory
        ));
    }

    public ResponseEntity<ResponseObject> create(ContractHistoryDTO contractHistoryDTO){
        ContractHistory contractHistory;
        contractHistoryDTO.setUsers(usersRepository.findById(contractHistoryDTO.getUsers().getId()).get());
        contractHistory = ContractHistory.builder()
                .price(contractHistoryDTO.getPrice())
                .description(contractHistoryDTO.getDescription())
                .image(contractHistoryDTO.getImage())
                .expiredTime(contractHistoryDTO.getExpiredTime())
                .users(contractHistoryDTO.getUsers())
                .build();
        contractHistoryRepository.save(contractHistory);
        return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                "Create contract history successfully",
                contractHistory
        ));
    }
    public ResponseEntity<ResponseObject> delete(Long id){
        Optional<ContractHistory> contractHistory = contractHistoryRepository.findById(id);
        if(contractHistory.isPresent()){
            contractHistory.get().setStatus(!contractHistory.get().isStatus());
            contractHistoryRepository.save(contractHistory.get());
            return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                    "Delete contract history successfully",
                    contractHistory
            ));
        }
        return ResponseEntity.status (HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found contract history",
                ""
        ));
    }
    public ResponseEntity<ResponseObject> update(Long id, ContractHistoryDTO contractHistoryDTO){
        Optional<ContractHistory> contractHistory = contractHistoryRepository.findById(id);
        if(contractHistory.isPresent()){
            if(contractHistoryDTO.getPrice()!=0){contractHistory.get().setPrice(contractHistoryDTO.getPrice());}
            if(contractHistoryDTO.getDescription()!=null){contractHistory.get().setDescription(contractHistoryDTO.getDescription());}
            if(contractHistoryDTO.getImage()!=null){contractHistory.get().setImage(contractHistoryDTO.getImage());}
            if(contractHistoryDTO.getExpiredTime()!=null){contractHistory.get().setExpiredTime(contractHistoryDTO.getExpiredTime());}
            if(contractHistoryDTO.getUsers()!=null){contractHistory.get().setUsers(usersRepository.findById(contractHistoryDTO.getUsers().getId()).get());}


            contractHistoryRepository.save(contractHistory.get());
            return ResponseEntity.status (HttpStatus.OK).body(new ResponseObject(
                    "Update contract history successfully",
                    contractHistory
            ));
        }
        return ResponseEntity.status (HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found contract history",
                ""
        ));
    }

}
