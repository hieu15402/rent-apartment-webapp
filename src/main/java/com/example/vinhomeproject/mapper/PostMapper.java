package com.example.vinhomeproject.mapper;


import com.example.vinhomeproject.dto.PostDTO_2;
import com.example.vinhomeproject.models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    void update(PostDTO_2 postDTO2, @MappingTarget Post post);
}
