package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Building;
import com.example.vinhomeproject.models.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building,Long> {
    @Query("SELECT b FROM Building b JOIN b.zone z JOIN z.area a WHERE a.id = :areaId")
    Page<Building> findAllBuildingsByAreaId(@Param("areaId") Long areaId, Pageable pageable);
    @Query("SELECT b FROM Building b WHERE b.zone.id = :zoneId")
    List<Building> findBuildingByZoneId(@Param("zoneId") Long zoneId);

    Optional<Building> findBuildingByNameAndZone(String name, Zone zone);
}
