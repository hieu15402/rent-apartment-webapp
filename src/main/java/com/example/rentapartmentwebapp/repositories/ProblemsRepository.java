package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Problems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemsRepository extends JpaRepository<Problems,Long> {
}
