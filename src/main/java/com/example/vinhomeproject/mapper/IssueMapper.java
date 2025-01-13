package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.IssueDTO;
import com.example.vinhomeproject.models.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IssueMapper {
    Issue toIssue(IssueDTO issueDTO);

    void update(IssueDTO issueDTO, @MappingTarget Issue issue);

}
