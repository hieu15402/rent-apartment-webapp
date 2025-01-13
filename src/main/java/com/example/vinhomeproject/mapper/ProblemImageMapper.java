package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.ProblemDTO;
import com.example.vinhomeproject.dto.ProblemImageDTO;
import com.example.vinhomeproject.models.ProblemImage;
import com.example.vinhomeproject.models.Problems;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProblemImageMapper {
    ProblemImage createProblemImages(ProblemImageDTO problemImageDTO);
    void update(ProblemImageDTO problemImageDTO, @MappingTarget ProblemImage problemImage);
}
