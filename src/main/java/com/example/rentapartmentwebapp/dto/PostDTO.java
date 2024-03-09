package com.example.rentapartmentwebapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private String title;
    private String description;

    private Long apartmentId;

    public PostDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
