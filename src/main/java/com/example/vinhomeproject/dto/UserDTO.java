package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Base;
import com.example.vinhomeproject.models.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class UserDTO {
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private LocalDate dateOfBirth;
    private boolean status;
    private String identityCard;
    private LocalDate identityCardDateProvide;
    private String identityCardAddressProvide;
    private String image;
    private String gender;
    private String address;
    private boolean isVerified;
}
