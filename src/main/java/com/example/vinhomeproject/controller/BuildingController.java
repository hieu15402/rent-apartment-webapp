package com.example.vinhomeproject.controller;

import com.example.vinhomeproject.dto.AreaDTO;
import com.example.vinhomeproject.dto.BuildingDTO;
import com.example.vinhomeproject.dto.PageList;
import com.example.vinhomeproject.models.Building;
import com.example.vinhomeproject.models.Post;
import com.example.vinhomeproject.response.ResponseObject;
import com.example.vinhomeproject.service.AreaService;
import com.example.vinhomeproject.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/buildings")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        return buildingService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        return buildingService.getById(id);
    }
    @PostMapping
    public ResponseEntity<ResponseObject> create(@RequestBody BuildingDTO buildingDTO){
        return buildingService.create(buildingDTO);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        return buildingService.delete(id);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id,@RequestBody BuildingDTO buildingDTO){
        return buildingService.update(id,buildingDTO);
    }
    @GetMapping("/get-page/{currentPage}")
    public ResponseEntity<ResponseObject> getPage(@PathVariable int currentPage, @RequestParam(defaultValue = "3") int sizePage, @RequestParam(defaultValue = "name") String field){
        if(buildingService.count() < sizePage){
            return buildingService.getAll();
        }
        Page<Building> buildings = buildingService.getPage(currentPage,sizePage,field);
        if(buildings.getTotalPages() < currentPage){
            return ResponseEntity.badRequest().body(new ResponseObject(
                    "Page number out of range",null
            ));
        }
        var pageList = PageList.<Building>builder()
                .totalPage(buildings.getTotalPages())
                .currentPage(currentPage)
                .listResult(buildings.getContent())
                .build();
        return ResponseEntity.ok(new ResponseObject(
                "Get page "+currentPage+" successfully",
                pageList
        ));
    }
    @GetMapping("/get-by-are/{id}")
    public ResponseEntity<ResponseObject> getByArea(@PathVariable Long id,@RequestParam(defaultValue = "1") int currentPage, @RequestParam(defaultValue = "3") int sizePage, @RequestParam(defaultValue = "name") String field){
        Page<Building> buildings = buildingService.filterByArea(id,currentPage,sizePage,field);
        if(buildings.getTotalPages() < currentPage){
            return ResponseEntity.badRequest().body(new ResponseObject(
                    "Page number out of range",null
            ));
        }
        var pageList = PageList.<Building>builder()
                .totalPage(buildings.getTotalPages())
                .currentPage(currentPage)
                .listResult(buildings.getContent())
                .build();
        return ResponseEntity.ok(new ResponseObject(
                "Get page "+currentPage+" successfully",
                pageList
        ));
    }
}
