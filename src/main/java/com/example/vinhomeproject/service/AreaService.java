package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.AppointmentDTO;
import com.example.vinhomeproject.dto.AreaDTO;
import com.example.vinhomeproject.models.Appointment;
import com.example.vinhomeproject.models.Area;
import com.example.vinhomeproject.repositories.AreaRepository;
import com.example.vinhomeproject.response.ResponseObject;
import com.google.type.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public ResponseEntity<ResponseObject> getAll(){
        List<Area> areas = areaRepository.findAll();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                areas
        ));
    }

    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<Area> area = areaRepository.findById(id);
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                area
        ));
    }
    public ResponseEntity<ResponseObject> create(AreaDTO areaDTO){
        Optional<Area> area_check = areaRepository.findAreaByName(areaDTO.getName());
        if(area_check.isEmpty()){
            Area area = new Area();
            area.setCreateDate(LocalDate.now());
            area.setStatus(true);
            area.setName(areaDTO.getName());
            areaRepository.save(area);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Create area successfully",
                    area
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Area have exist",
                ""
        ));
    }
    public ResponseEntity<String> delete(Long id){
        Optional<Area> area = areaRepository.findById(id);
        if(area.isPresent()){
            area.get().setStatus(!area.get().isStatus());
            areaRepository.save(area.get());
            return ResponseEntity.ok("successfully");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    public ResponseEntity<ResponseObject> update(Long id, AreaDTO areaDTO){
        Optional<Area> area = areaRepository.findById(id);
        if(area.isPresent()){
            if(areaRepository.findAreaByName(areaDTO.getName()).isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Area have exist",
                        area
                ));
            }
            if(areaDTO.getName()!=null){area.get().setName(areaDTO.getName());}
            areaRepository.save(area.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update area successfully",
                    area
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found area",
                ""
        ));
    }
}
