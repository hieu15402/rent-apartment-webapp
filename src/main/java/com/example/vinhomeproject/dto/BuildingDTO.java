package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Zone;
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
