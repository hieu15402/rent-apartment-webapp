package com.example.rentapartmentwebapp.dto;

import com.example.rentapartmentwebapp.models.Contract;
import com.example.rentapartmentwebapp.models.Issue;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class ProblemDTO {
    private String title;
    private String description;
    private boolean status;
    private Contract contract;
    private Set<Issue> issues;
}
