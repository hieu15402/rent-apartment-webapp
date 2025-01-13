package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.ApartmentClassDTO;
import com.example.vinhomeproject.models.ApartmentClass;
import com.example.vinhomeproject.repositories.ApartmentClassRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ApartmentClassService {
    @Autowired
    private ApartmentClassRepository apartmentClassRepository;

    public ResponseEntity<ResponseObject> getAll(){
        List<ApartmentClass> apartmentClasses = apartmentClassRepository.findAll();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                apartmentClasses
        ));
    }

    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<ApartmentClass> apartmentClass = apartmentClassRepository.findById(id);
        if(apartmentClass.isPresent()){
            return ResponseEntity.ok(new ResponseObject(
                    "successfully",
                    apartmentClass
            ));
        }
        return ResponseEntity.badRequest().body(new ResponseObject(
                "failed",
                null
        ));
    }

    public ResponseEntity<String> create(ApartmentClassDTO apartmentClassDTO){
        apartmentClassRepository.save(ApartmentClass.builder()
                        .name(apartmentClassDTO.getName())
                        .width(apartmentClassDTO.getWidth())
                        .length(apartmentClassDTO.getLength())
                        .rent_price(apartmentClassDTO.getRent_price())
                        .height(apartmentClassDTO.getHeight())
                        .build());
        return ResponseEntity.ok("create successfully");
    }

    public ResponseEntity<String> delete(Long id){
        Optional<ApartmentClass> apartmentClass = apartmentClassRepository.findById(id);
        if(apartmentClass.isPresent()){
            apartmentClass.get().setStatus(!apartmentClass.get().isStatus());
            apartmentClassRepository.save(apartmentClass.get());
            return ResponseEntity.ok("delete successfully");
        }
        return ResponseEntity.badRequest().body("id not exist");
    }

    public ResponseEntity<String> update(Long id,ApartmentClassDTO apartmentClassDTO){
        Optional<ApartmentClass> apartmentClass = apartmentClassRepository.findById(id);
        if(apartmentClass.isPresent()){
            if (apartmentClassDTO.getName()!=null){
            apartmentClass.get().setName(apartmentClassDTO.getName());
            }
            if(apartmentClassDTO.getWidth() !=0 ){
            apartmentClass.get().setWidth(apartmentClassDTO.getWidth());
            }
            if(apartmentClassDTO.getLength() !=0){
            apartmentClass.get().setLength(apartmentClassDTO.getLength());}
            if (apartmentClassDTO.getHeight() !=0){
            apartmentClass.get().setHeight(apartmentClassDTO.getHeight());}
            if (apartmentClassDTO.getRent_price() != 0){
            apartmentClass.get().setRent_price(apartmentClassDTO.getRent_price());}
            apartmentClassRepository.save(apartmentClass.get());
            return ResponseEntity.ok("update successfully");
        }
        return ResponseEntity.badRequest().body("id not exist");
    }
}
