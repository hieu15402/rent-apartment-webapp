package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.ProblemImageDTO;
import com.example.vinhomeproject.models.PostImage;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.PostImageService;
import com.example.vinhomeproject.service.ProblemImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/problem-image")
public class ProblemImageController {
    @Autowired
    private ProblemImageService service;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateP(@PathVariable Long id,@RequestBody ProblemImageDTO problemImageDTO) {
        return service.update(id,problemImageDTO);
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<String> createPostImage(@RequestParam("file") MultipartFile multipartFile,
                                                  @PathVariable Long id ) {
        return service.createPostImage(multipartFile, id);
    }
}
