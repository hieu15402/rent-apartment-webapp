package com.example.rentapartmentwebapp.dto;

import com.example.rentapartmentwebapp.models.Zone;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingDTO {
    private String name;
    private Zone zone;
}
