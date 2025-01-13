package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class UserDTO_2 {
    private Long id;
    private String email;
    private String password;
    private String phone;
    private String fullName;
    private LocalDate dateOfBirth;
    private boolean status;
    private String image;
    private String gender;
    private String address;
    private boolean isVerified;
    private Set<Appointment> appointments;
}
