package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.ApartmentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentClassRepository extends JpaRepository<ApartmentClass,Long> {
}
