package com.example.vinhomeproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class UpdateUserDTO {
    private String phone;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String identityCard;
    private LocalDate identityCardDateProvide;
    private String identityCardAddressProvide;
    private String address;
}
