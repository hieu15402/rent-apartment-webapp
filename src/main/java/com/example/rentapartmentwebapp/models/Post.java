package com.example.rentapartmentwebapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table
public class Post extends Base{
    private String title;
    private String description;

    @OneToMany(mappedBy = "post")
    private Set<PostImage> postImages;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;
}
