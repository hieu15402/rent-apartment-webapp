package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.ApartmentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentClassRepository extends JpaRepository<ApartmentClass,Long> {
}
