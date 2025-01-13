package com.example.vinhomeproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cartNumber;
    private String releaseDate;
    private boolean status;
    private String bankCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
