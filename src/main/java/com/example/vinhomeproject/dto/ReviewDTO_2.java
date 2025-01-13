package com.example.vinhomeproject.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDTO_2 {
    private String content;
    private double rate;
    private Long apartmentId;
    private Long usersid;
    private boolean status;
}
