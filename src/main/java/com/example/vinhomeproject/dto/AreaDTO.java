package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Zone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaDTO {
    private String name;

}
