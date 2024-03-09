package com.example.rentapartmentwebapp.dto;

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
public class CartDTO_2 {
    private String name;
    private String cartNumber;
    private String releaseDate;
    private String bankCode;
}