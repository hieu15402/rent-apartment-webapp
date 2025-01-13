package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Users;
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
public class CartDTO {
    private String name;
    private String cartNumber;
    private String releaseDate;
    private String bankCode;
    private Users users;
}
