package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.ApartmentClassDTO;
import com.example.vinhomeproject.dto.IssueDTO;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/issue")
public class IssueController {
    @Autowired
    private IssueService service;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return service.getById(id);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody IssueDTO issueDTO){
        return service.createIssue(issueDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        return service.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody IssueDTO issueDTO){
        return service.update(id,issueDTO);
    }
}
