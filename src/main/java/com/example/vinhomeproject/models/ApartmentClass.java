package com.example.vinhomeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
public class ApartmentClass extends Base{
    private String name;

    private double rent_price;

    private double buy_price;

    private double width;

    private double length;

    private double height;

    @OneToMany(mappedBy = "apartmentClass")
    @JsonIgnore
    private Set<Apartment> apartments;
}
