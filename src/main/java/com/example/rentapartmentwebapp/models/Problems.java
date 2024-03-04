package com.example.rentapartmentwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Problems extends Base{

    private String title;

    private String description;

    private boolean status;

    @OneToMany(mappedBy = "problems")
    private Set<Issue> issues;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    private Contract contract;

    @OneToMany(mappedBy = "problems")
    private Set<ProblemImage> problemImages;
}
