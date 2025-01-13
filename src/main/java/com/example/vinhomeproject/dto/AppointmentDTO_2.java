package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.models.Users;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO_2 {
    private String statusAppointment;
    private LocalDate dateTime;
    private String time;
    private Apartment apartment;
    private String note;
    private String address;
    private String userAddress;
    private String userPhone;
    private String userName;
    private String userImage;


}
