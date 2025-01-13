package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.ApartmentClassDTO;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.ApartmentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/apartmentclasses")
public class ApartmentClassController {
    @Autowired
    private ApartmentClassService apartmentClassSerivce;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return apartmentClassSerivce.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return apartmentClassSerivce.getById(id);
    }
    @PostMapping
    public ResponseEntity<String> create(@RequestBody ApartmentClassDTO apartmentClassDTO){
        return apartmentClassSerivce.create(apartmentClassDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return apartmentClassSerivce.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ApartmentClassDTO apartmentClassDTO){
        return apartmentClassSerivce.update(id,apartmentClassDTO);
    }
}
