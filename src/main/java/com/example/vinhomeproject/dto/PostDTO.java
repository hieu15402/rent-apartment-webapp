package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.models.PostImage;
import lombok.*;

import java.util.Set;

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
