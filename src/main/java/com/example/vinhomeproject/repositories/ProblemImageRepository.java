package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.ProblemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemImageRepository extends JpaRepository<ProblemImage,Long> {
    Optional<ProblemImage> findById(Long id);
}
