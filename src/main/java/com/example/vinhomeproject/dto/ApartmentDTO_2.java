package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.ApartmentClass;
import com.example.vinhomeproject.models.Area;
import com.example.vinhomeproject.models.Building;
import com.example.vinhomeproject.models.Zone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApartmentDTO_2 {
    private Long id;
    private String name;

    private String description;

    private int living_room;

    private int bed_room;

    private int kitchen;

    private int rest_room;

    private int floor;

    private double area;

    private boolean status;

    private int air_conditioner;

    private int electric_fan;

    private int television;

    private int electric_stoves;

    private int gas_stoves;

    private Building building;

    private Zone zone;

    private Area area_c;

    public ApartmentDTO_2(String name, String description, int livingRoom, int bedRoom, int kitchen, int restRoom, int floor, double area, boolean status, int airConditioner, int electricFan, int television, int electricStoves, int gasStoves) {
        this.name = name;
        this.description = description;
        this.living_room = livingRoom;
        this.bed_room = bedRoom;
        this.kitchen = kitchen;
        this.rest_room = restRoom;
        this.floor = floor;
        this.area = area;
        this.status = status;
        this.air_conditioner = airConditioner;
        this.electric_fan = electricFan;
        this.television = television;
        this.electric_stoves = electricStoves;
        this.gas_stoves = gasStoves;
    }
}
