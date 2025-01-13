package com.example.vinhomeproject.service;

import com.example.vinhomeproject.dto.ApartmentDTO;
import com.example.vinhomeproject.dto.ApartmentDTO_2;
import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.repositories.ApartmentClassRepository;
import com.example.vinhomeproject.repositories.ApartmentRepository;
import com.example.vinhomeproject.repositories.BuildingRepository;
import com.example.vinhomeproject.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ApartmentClassRepository apartmentClassRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    public ResponseEntity<ResponseObject> getAll(){
        List<Apartment> apartments = apartmentRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Get list apartment successfully",
                apartments
        ));
    }

    public ResponseEntity<ResponseObject> getById(Long id){
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        if(apartment.isPresent()){
            return ResponseEntity.ok(new ResponseObject(
                    "Get apartment by id successfully",
                    apartment
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found apartment",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> create(ApartmentDTO apartmentDTO){
        Optional<Apartment> check = apartmentRepository.findApartmentByNameAndBuilding(apartmentDTO.getName(),buildingRepository.findById(apartmentDTO.getBuilding().getId()).get());
        if(check.isEmpty()){
            apartmentDTO.setApartmentClass(apartmentClassRepository.findById(apartmentDTO.getApartmentClass().getId()).get());
            apartmentDTO.setBuilding(buildingRepository.findById(apartmentDTO.getBuilding().getId()).get());
            Apartment apartment;
            apartmentRepository.save(apartment = Apartment.builder()
                    .name(apartmentDTO.getName())
                    .description(apartmentDTO.getDescription())
                    .living_room(apartmentDTO.getLiving_room())
                    .bed_room(apartmentDTO.getBed_room())
                    .kitchen(apartmentDTO.getKitchen())
                    .rest_room(apartmentDTO.getRest_room())
                    .floor(apartmentDTO.getFloor())
                    .area(apartmentDTO.getArea())
                    .air_conditioner(apartmentDTO.getAir_conditioner())
                    .electric_fan(apartmentDTO.getElectric_fan())
                    .television(apartmentDTO.getTelevision())
                    .electric_stoves(apartmentDTO.getElectric_stoves())
                    .gas_stoves(apartmentDTO.getGas_stoves())
                    .apartmentClass(apartmentDTO.getApartmentClass())
                    .building(apartmentDTO.getBuilding())
                    .build());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Create apartment successfully",
                    apartment
            ));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Apartment have exist",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> delete(Long id){
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        if(apartment.isPresent()){
            apartment.get().setStatus(!apartment.get().isStatus());
            apartmentRepository.save(apartment.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Delete apartment successfully",
                    apartment
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found apartment",
                ""
        ));
    }

    public ResponseEntity<ResponseObject> update(Long id, ApartmentDTO apartmentDTO){
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        if(apartment.isPresent()){
            if(apartmentRepository.findApartmentByNameAndBuilding(apartmentDTO.getName(),apartment.get().getBuilding()).isPresent()
            && !apartment.get().getName().equals(apartmentDTO.getName())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Apartment have exist",
                        ""
                ));
            }
            if(apartmentDTO.getName()!=null){apartment.get().setName(apartmentDTO.getName());}
            if(apartmentDTO.getDescription()!=null){apartment.get().setDescription(apartmentDTO.getDescription());}
            if(apartmentDTO.getLiving_room()!=0){ apartment.get().setLiving_room(apartmentDTO.getLiving_room());}
            if(apartmentDTO.getBed_room()!=0){apartment.get().setBed_room(apartmentDTO.getBed_room());}
            if (apartmentDTO.getKitchen()!=0){apartment.get().setKitchen(apartmentDTO.getKitchen());}
            if (apartmentDTO.getRest_room()!=0){apartment.get().setRest_room(apartmentDTO.getRest_room());}
            if (apartmentDTO.getFloor()!=0){apartment.get().setFloor(apartmentDTO.getFloor());}
            if (apartmentDTO.getArea()!=0){apartment.get().setArea(apartmentDTO.getArea());}
            if (apartmentDTO.getAir_conditioner()!=0){apartment.get().setAir_conditioner(apartmentDTO.getAir_conditioner());}
            if (apartmentDTO.getElectric_fan()!=0){apartment.get().setElectric_fan(apartmentDTO.getElectric_fan());}
            if (apartmentDTO.getTelevision()!=0){apartment.get().setTelevision(apartmentDTO.getTelevision());}
            if (apartmentDTO.getElectric_stoves()!=0){apartment.get().setElectric_stoves(apartmentDTO.getElectric_stoves());}
            if (apartmentDTO.getGas_stoves()!=0){apartment.get().setGas_stoves(apartmentDTO.getGas_stoves());}
            if (apartmentDTO.getApartmentClass()!=null){apartment.get().setApartmentClass(apartmentClassRepository.findById(apartmentDTO.getApartmentClass().getId()).get());}
            if (apartmentDTO.getBuilding()!=null){apartment.get().setBuilding(buildingRepository.findById(apartmentDTO.getBuilding().getId()).get());}
            apartmentRepository.save(apartment.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Update apartment successfully",
                    apartment
            ));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Not found apartment",
                ""
        ));
    }
    public ResponseEntity<ResponseObject> countAll(){
        List<Apartment> apartments = apartmentRepository.findAll();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                apartments.size()
        ));
    }

    public ResponseEntity<ResponseObject> findAllApartmentsWithDetails(){
        Set<ApartmentDTO_2> apartments = apartmentRepository.findAllApartmentsWithDetails();
        return ResponseEntity.ok(new ResponseObject(
                "successfully",
                apartments
        ));
    }
    public ResponseEntity<ResponseObject> findApartmentByIdWithDetails(Long id){
        ApartmentDTO_2 apartment = apartmentRepository.findApartmentByIdWithDetails(id);
        if(apartment != null){
            return ResponseEntity.ok(new ResponseObject(
                    "successfully",
                    apartment
            ));
        }
        return ResponseEntity.ok(new ResponseObject(
                "failed",
                null
        ));
    }

    public Page<Apartment> getPage(int currentPage, int pageSize, String field) {
        return apartmentRepository.findAll(PageRequest.of(currentPage-1, pageSize, Sort.by(Sort.Direction.ASC, field)));
    }
    public int count(){
        return apartmentRepository.findAll().size();
    }
}
