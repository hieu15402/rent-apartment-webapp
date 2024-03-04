package com.example.rentapartmentwebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Payment extends Base{

    private LocalDate payment_time;

    private String content;

    private double total_price;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

}
