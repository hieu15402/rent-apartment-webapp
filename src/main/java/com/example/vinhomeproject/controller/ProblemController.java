package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.ProblemDTO;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.ProblemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/problem")
public class ProblemController {

    @Autowired
    private ProblemsService serivce;

    @GetMapping()
    public ResponseEntity<ResponseObject> getAllProblems(){
        return serivce.getListProblems();
    }

    @PostMapping("")
    public  ResponseEntity<ResponseObject> createProblems(@RequestBody ProblemDTO problemDTO){
        return serivce.createProblems(problemDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteProblems(@PathVariable Long id){
        return  serivce.deleteProblems(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateProblems(@PathVariable Long id,@RequestBody ProblemDTO problemDTO){
        return  serivce.updateProblems(id,problemDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getbyId(@PathVariable Long id){
        return serivce.getProblemsById(id);
    }
}
