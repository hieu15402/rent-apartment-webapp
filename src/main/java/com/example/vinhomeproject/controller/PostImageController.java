package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.PostDTO_2;
import com.example.vinhomeproject.models.PostImage;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/postimage")
public class PostImageController {
    private PostImageService sv;

    @Autowired
    public void PostImageSerivce(PostImageService sv) {
        this.sv = sv;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getPostImage() {
        return sv.getAllPostImage();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getPostImageById(@PathVariable Long id){
        return sv.getPostImageById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePostImage(@PathVariable Long id) throws IOException, URISyntaxException {
        return sv.deletePostImage(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePostImage(@RequestParam("file") MultipartFile multipartFile,@PathVariable Long id) {
        return sv.updatePostImage(id,multipartFile);
    }

    @PostMapping("/create/{post_id}")
    public ResponseEntity<ResponseObject> createPostImage(@RequestParam("file") MultipartFile multipartFile,
                                                  @PathVariable Long post_id ) {
        return sv.createPostImage(multipartFile, post_id);
    }
}
