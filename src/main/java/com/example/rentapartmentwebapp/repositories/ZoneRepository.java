package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone,Long> {
    @Query("SELECT z FROM Zone z WHERE z.area.id = :areaId")
    List<Zone> findZoneByAreaId(@Param("areaId") Long areaId);
}
