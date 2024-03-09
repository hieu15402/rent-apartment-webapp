package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area,Long> {
}
