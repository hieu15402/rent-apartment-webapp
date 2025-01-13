package com.example.vinhomeproject.service;


import com.example.vinhomeproject.dto.ZoneDTO;
import com.example.vinhomeproject.dto.ZoneDTO_2;
import com.example.vinhomeproject.mapper.ZoneMapper;
import com.example.vinhomeproject.models.Zone;
import com.example.vinhomeproject.repositories.AreaRepository;
import com.example.vinhomeproject.repositories.ZoneRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ZoneService {

    @Autowired
    private ZoneRepository repo;
    @Autowired
    private ZoneMapper mapper;
    @Autowired
    private AreaRepository areaRepository;

    public ResponseEntity<ResponseObject> getAllZone(){
        List<Zone> zones = repo.findAll();
        if(!zones.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get all zone successfully",
                    zones
            ));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "List zone null",
                    null
            ));
        }
    }

    public ResponseEntity<ResponseObject> createZone(ZoneDTO zoneDTO){
        Optional<Zone> zone_check = repo.findZoneByAreaAndName( areaRepository.findById(zoneDTO.getArea().getId()).get(),zoneDTO.getName());
        if(zone_check.isEmpty()){
            zoneDTO.setStatus(true);
            zoneDTO.setArea(areaRepository.findById(zoneDTO.getArea().getId()).get());
            Zone zone = mapper.createZoneToZoneDto(zoneDTO);
            zone.setCreateDate(LocalDate.now());
            repo.save(zone);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Create zone successfully",
                    zone
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Zone have exist",
                ""
        ));
    }
    public ResponseEntity<ResponseObject>  deleteZone(Long id){
        Optional<Zone> zone = repo.findById(id);
        if(zone.isPresent()){
            zone.get().setStatus(!zone.get().isStatus());
            repo.save(zone.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Delete zone successfully",
                    null
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                    "Not found zone with id: " + id,
                    null
            ));
        }

    }

    public ResponseEntity<ResponseObject>  updateZone(Long id,ZoneDTO zoneDTO){
        Optional<Zone> zone = repo.findById(id);
        if(zone.isPresent()){
            if(repo.findZoneByAreaAndName(areaRepository.findById(zoneDTO.getArea().getId()).get(), zoneDTO.getName()).isPresent()){
                return ResponseEntity.ok(new ResponseObject(
                        "Zone have exist",
                        zone
                ));
            }
            zoneDTO.setArea(areaRepository.findById(zoneDTO.getArea().getId()).get());
            mapper.update(zoneDTO,zone.get());
            repo.save(zone.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update zone successfully",
                    zone
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                    "Not found zone with id: " + id,
                    ""
            ));
        }
    }
    //f
    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<Zone> zone = repo.findById(id);
        if(zone.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Get zone with id: "+ id + " successfully",
                    zone.get()
            ));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                    "Not found zone with id " + id,
                    null
            ));
        }
    }
    public ResponseEntity<ResponseObject> countBuildingInZone(){
        List<Zone> zones = repo.findAll();
        List<ZoneDTO_2> zoneDTO2s = new ArrayList<>();
        for (Zone zone : zones) {
            ZoneDTO_2 zoneDTO_2 = new ZoneDTO_2(zone.getName(), zone.getBuildings().size());
            zoneDTO2s.add(zoneDTO_2);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Get successfully",
                zoneDTO2s
        ));
    }
}


