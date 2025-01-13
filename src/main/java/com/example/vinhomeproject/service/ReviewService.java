package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.ReviewDTO;
import com.example.vinhomeproject.dto.ReviewDTO_2;
import com.example.vinhomeproject.dto.ReviewDTO_3;
import com.example.vinhomeproject.mapper.ReviewMapper;
import com.example.vinhomeproject.models.Review;
import com.example.vinhomeproject.repositories.ReviewRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repo;
    @Autowired
    private ReviewMapper mapper;

    public ResponseEntity<ResponseObject> getAllReview(){
        List<ReviewDTO_3> reviews = repo.findAllReview();
        if(!reviews.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get all review successfully",
                    reviews
            ));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "List review null",
                    null
            ));
        }
    }

    public ResponseEntity<ResponseObject> createReview(ReviewDTO reviewDTO){
        reviewDTO.setStatus(true);
        repo.save(mapper.createProblemsToProblemsDto(reviewDTO));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Create review successfully",
                null
        ));
    }
    public ResponseEntity<ResponseObject>  deleteReview(Long id){
        Optional<Review> review = repo.findById(id);
        if(review.isPresent()){
            review.get().setStatus(!review.get().isStatus());
            repo.save(review.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Delete review successfully",
                    null
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found review by id " +id,
                null
        ));
    }

    public ResponseEntity<ResponseObject>  updateReview(Long id,ReviewDTO reviewDTO){
        Optional<Review> review = repo.findById(id);
        if(review.isPresent()){
            mapper.update(reviewDTO,review.get());
            repo.save(review.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update review successfully",
                    null
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found review by id " + id,
                null
        ));
    }
    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<Review> review = repo.findById(id);
        if(review.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get review by id " + id +" successfully",
                    review
            ));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Not found review by id " +id,
                    null
            ));
        }
    }
    public Page<ReviewDTO_2> getPage(int currentPage, int pageSize, String field) {
        Page<Review> reviews = repo.findAll(PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.ASC, field)));

        List<ReviewDTO_2> reviewDTOs = reviews.getContent().stream()
                .map(mapper::reviewToReviewDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(reviewDTOs, reviews.getPageable(), reviews.getTotalElements());
    }
    public int count(){
        return repo.findAll().size();
    }


}
