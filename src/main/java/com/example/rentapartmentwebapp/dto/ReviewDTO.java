package com.example.rentapartmentwebapp.dto;

import com.example.rentapartmentwebapp.models.Apartment;
import com.example.rentapartmentwebapp.models.Users;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class ReviewDTO {
    private String content;
    private double rate;
    private Users users;
    private Apartment apartment;
    private boolean status;
}
