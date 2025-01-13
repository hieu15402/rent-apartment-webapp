package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Area;
import com.example.vinhomeproject.models.Building;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
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
