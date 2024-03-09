package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Long> {
    @Query("SELECT b FROM Building b JOIN b.zone z JOIN z.area a WHERE a.id = :areaId")
    Page<Building> findAllBuildingsByAreaId(@Param("areaId") Long areaId, Pageable pageable);
    @Query("SELECT b FROM Building b WHERE b.zone.id = :zoneId")
    List<Building> findBuildingByZoneId(@Param("zoneId") Long zoneId);

}
