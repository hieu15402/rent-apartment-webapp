package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.ProblemDTO;
import com.example.vinhomeproject.models.Problems;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProblemsMapper {

    Problems createProblemsToProblemsDto(ProblemDTO problemDTO);
    void update(ProblemDTO problemDTO, @MappingTarget Problems problems);
}
