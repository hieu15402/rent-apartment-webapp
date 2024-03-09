package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
