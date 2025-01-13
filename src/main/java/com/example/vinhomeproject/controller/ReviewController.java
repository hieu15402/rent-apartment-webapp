package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.PageList;
import com.example.vinhomeproject.dto.ReviewDTO;
import com.example.vinhomeproject.dto.ReviewDTO_2;
import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.models.Review;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/review")
public class ReviewController {
    @Autowired
    private ReviewService serivce;
    @GetMapping()
    public ResponseEntity<ResponseObject> getAllReview(){
        return serivce.getAllReview();
    }
    @PostMapping("")
    public  ResponseEntity<ResponseObject> createReview(@RequestBody ReviewDTO review){
        return serivce.createReview(review);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteReview(@PathVariable Long id){
        return  serivce.deleteReview(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateReview(@PathVariable Long id,@RequestBody ReviewDTO userDTO){
        return  serivce.updateReview(id,userDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getbyId(@PathVariable Long id){
        return serivce.getById(id);
    }
    @GetMapping("/get-page/{currentPage}")
    public ResponseEntity<ResponseObject> getPage(@PathVariable int currentPage, @RequestParam(defaultValue = "3") int sizePage, @RequestParam(defaultValue = "content") String field){
        if(sizePage > serivce.count()){
            return serivce.getAllReview();
        }
        Page<ReviewDTO_2> reviews = serivce.getPage(currentPage,sizePage,field);
        if(reviews.getTotalPages() < currentPage){
            return ResponseEntity.badRequest().body(new ResponseObject(
                    "Page number out of range",""
            ));
        }
        var pageList = PageList.<ReviewDTO_2>builder()
                .totalPage(reviews.getTotalPages())
                .currentPage(currentPage)
                .listResult(reviews.getContent())
                .build();
        return ResponseEntity.ok(new ResponseObject(
                "Get page "+currentPage+" successfully",
                pageList
        ));
    }

}
