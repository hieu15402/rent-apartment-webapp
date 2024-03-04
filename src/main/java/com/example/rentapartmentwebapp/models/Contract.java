package com.example.rentapartmentwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract extends Base{
    private LocalDate dateSign;
    private String description;
    private LocalDate dateStartRent;

    @OneToMany(mappedBy = "contract")
    @JsonIgnore
    private Set<Problems> problems;

    @OneToMany(mappedBy = "contract")
    private Set<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractHistory contractHistory;
}
