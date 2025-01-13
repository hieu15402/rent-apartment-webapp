package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Problems;
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
