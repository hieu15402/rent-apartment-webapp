package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.dto.ReviewDTO_3;
import com.example.vinhomeproject.models.Review;
import com.example.vinhomeproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("select new com.example.vinhomeproject.dto.ReviewDTO_3(r.id, r.content, r.status, r.rate, a.id, a.name," +
            "a.description,u.id, u.fullName, u.email, u.image, " +
            "a.building.name, " +
            "a.building.zone.name, " +
            "a.building.zone.area.name )" +
            "from Review r " +
            "join Apartment a on r.apartment.id = a.id " +
            "join Users u on r.users.id = u.id")
    List<ReviewDTO_3> findAllReview();
}
