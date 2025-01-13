package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.AppointmentDTO;
import com.example.vinhomeproject.dto.AreaDTO;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/areas")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        return areaService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return areaService.getById(id);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody AreaDTO areaDTO){
        return areaService.create(areaDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return areaService.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id,@RequestBody AreaDTO areaDTO){
        return areaService.update(id,areaDTO);
    }
}
