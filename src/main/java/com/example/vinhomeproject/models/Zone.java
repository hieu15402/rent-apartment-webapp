package com.example.vinhomeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Zone extends Base{
    private String name;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @OneToMany(mappedBy = "zone")
    @JsonIgnore
    private List<Building> buildings;
}
