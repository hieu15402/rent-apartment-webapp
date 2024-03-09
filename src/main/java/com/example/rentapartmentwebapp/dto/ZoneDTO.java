package com.example.rentapartmentwebapp.dto;

import com.example.rentapartmentwebapp.models.Area;
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
public class ZoneDTO {
    private String name;
    private Area area;
    private boolean status;
}
