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
public class Apartment extends Base{
    private String name;

    private String description;

    private int living_room;

    private int bed_room;

    private int kitchen;

    private int rest_room;

    private int floor;

    private double area;

    private int air_conditioner;

    private int electric_fan;

    private int television;

    private int electric_stoves;

    private int gas_stoves;

    @ManyToOne
    @JoinColumn(name = "apartment_class_id")
    private ApartmentClass apartmentClass;


    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "apartment")
    @JsonIgnore
    private Set<Review> reviews;

    @OneToMany(mappedBy = "apartment")
    @JsonIgnore
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "apartment")
    @JsonIgnore
    private Set<Post> posts;

    @OneToMany(mappedBy = "apartment")
    @JsonIgnore
    private Set<Problems> problems;
}
