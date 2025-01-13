package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.ContractDTO;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/contracts")
public class ContractController {
    @Autowired
    private ContractService contractService;
    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        return contractService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return contractService.getById(id);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody ContractDTO contractDTO){
        return contractService.create(contractDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        return contractService.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id,@RequestBody ContractDTO contractDTO){
        return contractService.update(id,contractDTO);
    }
    @GetMapping("/count-all")
    public ResponseEntity<ResponseObject> countAll(){
        return contractService.countAll();
    }
    @PutMapping("/upload/{id}")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile multipartFile,
                                                     @PathVariable Long id){
       return contractService.uploadFile(multipartFile,id);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws IOException {
        return contractService.downloadFile(id);
    }
    @GetMapping("/getDetailContract/{id}")
    public ResponseEntity<ResponseObject> getDetailContract(@PathVariable Long id){
        return contractService.getDetailContract(id);
    }
    @GetMapping("/getListContract/{id}")
    public ResponseEntity<ResponseObject> getListContract(@PathVariable Long id){
        return contractService.getListContract(id);
    }
}
