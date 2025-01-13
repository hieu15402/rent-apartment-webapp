package com.example.vinhomeproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAppointmentDTO {
    private String name;
    private String image_url;
    private String phone;
    private String address;
}
