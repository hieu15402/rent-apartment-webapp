package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.ReviewDTO;
import com.example.vinhomeproject.dto.ReviewDTO_2;
import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.models.Review;
import com.example.vinhomeproject.models.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review createProblemsToProblemsDto(ReviewDTO reviewDTO);
    void update(ReviewDTO reviewDTO, @MappingTarget Review review);

    @Mapping(target = "apartmentId", source = "apartment")
    @Mapping(target = "usersid", source = "users")
    ReviewDTO_2 reviewToReviewDTO(Review review);

    default Long map(Apartment value) {
        return value.getId();
    }
    default Long map(Users value) {
        return value.getId();
    }

}
