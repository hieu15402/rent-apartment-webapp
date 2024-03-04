package com.example.rentapartmentwebapp.models;

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
