package com.example.rentapartmentwebapp.dto;

import com.example.rentapartmentwebapp.models.Apartment;
import com.example.rentapartmentwebapp.models.Users;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private String statusAppointment;
    private LocalDate dateTime;
    private Users users;
    private Apartment apartment;
}
