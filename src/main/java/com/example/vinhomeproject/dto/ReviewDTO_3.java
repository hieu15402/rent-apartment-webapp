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
public class ReviewDTO_3 {
    private Long reviewId;
    private String content;
    private boolean status;
    private double rate;
    private Long apartmentId;
    private String apartmentName;
    private String apartmentDescription;
    private Long userId;
    private String userName;
    private String email;
    private String userImage;
    private String buildingName;
    private String zoneName;
    private String areaName;
}
