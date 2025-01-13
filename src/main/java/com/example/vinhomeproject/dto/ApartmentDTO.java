package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.ApartmentClass;
import com.example.vinhomeproject.models.Building;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentDTO {
    private String name;

    private String description;

    private int living_room;

    private int bed_room;

    private int kitchen;

    private int rest_room;

    private int floor;

    private int area;

    private boolean status;

    private int air_conditioner;

    private int electric_fan;

    private int television;

    private int electric_stoves;

    private int gas_stoves;

    private ApartmentClass apartmentClass;

    private Building building;
}
