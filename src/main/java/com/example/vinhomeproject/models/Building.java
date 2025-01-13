package com.example.vinhomeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class Building extends Base{
    private String name;

    @ManyToOne
    @JoinColumn(name = "zone")
    private Zone zone;

}
