package com.example.rentapartmentwebapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueDTO {
    private String name;
    private boolean status;
}
