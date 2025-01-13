package com.example.vinhomeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
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
    private String urlFile;
    @Column(nullable = true)
    private boolean statusOfPayment;
    @OneToMany(mappedBy = "contract")
    @JsonIgnore
    private Set<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractHistory contractHistory;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    @JsonIgnore
    private Appointment appointment;
}
