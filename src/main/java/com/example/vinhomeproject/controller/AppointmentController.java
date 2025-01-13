package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.AppointmentDTO;
import com.example.vinhomeproject.dto.AppointmentUpdateDTO;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        return appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return appointmentService.getById(id);
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> create(@RequestBody AppointmentDTO appointmentDTO){
        return appointmentService.create(appointmentDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        return appointmentService.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id,@RequestBody AppointmentUpdateDTO appointmentDTO){
        return appointmentService.update(id,appointmentDTO);
    }
    @GetMapping("/get-by-userid/{userId}")
    public ResponseEntity<ResponseObject> getByUserId(@PathVariable Long userId){
        return appointmentService.getbyUserId(userId);
    }
}
