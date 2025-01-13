package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area,Long> {
    Optional<Area> findAreaByName(String name);
}
