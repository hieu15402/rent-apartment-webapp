package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.ProblemDTO;
import com.example.vinhomeproject.dto.ZoneDTO;
import com.example.vinhomeproject.models.Problems;
import com.example.vinhomeproject.models.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ZoneMapper {
    Zone createZoneToZoneDto(ZoneDTO zoneDTO);
    void update(ZoneDTO zoneDTO, @MappingTarget Zone zone);
}
