package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.ApartmentDTO;
import com.example.vinhomeproject.dto.PageList;
import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/apartments")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll(){
        return apartmentService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return apartmentService.getById(id);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody ApartmentDTO apartmentDTO){
        return apartmentService.create(apartmentDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        return apartmentService.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id,@RequestBody ApartmentDTO apartmentDTO){
        return apartmentService.update(id, apartmentDTO);
    }
    @GetMapping("/count-all")
    public ResponseEntity<ResponseObject> countAll(){
        return apartmentService.countAll();
    }

    @GetMapping("/get-all-details")
    public ResponseEntity<ResponseObject> getAllDetails(){
        return apartmentService.findAllApartmentsWithDetails();
    }
    @GetMapping("/get-details/{id}")
    public ResponseEntity<ResponseObject> getByIdDetail(@PathVariable Long id){
        return apartmentService.findApartmentByIdWithDetails(id);
    }
    @GetMapping("/get-page/{currentPage}")
    public ResponseEntity<ResponseObject> getPage(@PathVariable int currentPage, @RequestParam(defaultValue = "3") int sizePage, @RequestParam(defaultValue = "name") String field){
        if(sizePage > apartmentService.count()){
            return apartmentService.getAll();
        }
        Page<Apartment> apartments = apartmentService.getPage(currentPage,sizePage,field);
        if(apartments.getTotalPages() < currentPage){
            return ResponseEntity.badRequest().body(new ResponseObject(
                    "Page number out of range",""
            ));
        }
        var pageList = PageList.<Apartment>builder()
                .totalPage(apartments.getTotalPages())
                .currentPage(currentPage)
                .listResult(apartments.getContent())
                .build();
        return ResponseEntity.ok(new ResponseObject(
                "Get page "+currentPage+" successfully",
                pageList
        ));
    }
}
