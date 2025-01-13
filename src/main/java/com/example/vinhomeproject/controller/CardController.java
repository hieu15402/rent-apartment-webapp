package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.CartDTO;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/card")
public class CardController {

    @Autowired
    private CartService service;
    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return service.getById(id);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody CartDTO cartDTO){
        return service.create(cartDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        return service.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody CartDTO cartDTO){
        return service.update(id,cartDTO);
    }
    @GetMapping("/by-user/{id}")
    public ResponseEntity<ResponseObject> getByUserId(@PathVariable Long id){
        return service.getByUserId(id);
    }
}
