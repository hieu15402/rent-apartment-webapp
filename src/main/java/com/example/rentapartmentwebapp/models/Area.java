package com.example.rentapartmentwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Area extends Base{
    private String name;

    @OneToMany(mappedBy = "area")
    @JsonIgnore
    private List<Zone> zones;
}
