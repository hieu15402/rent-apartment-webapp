package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.dto.PostDTO;
import com.example.vinhomeproject.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT NEW com.example.vinhomeproject.dto.PostDTO(p.title, p.description, a.id) " +
            "FROM Post p " +
            "JOIN p.apartment a ")
    Set<PostDTO> findAllPosts();
    @Query("SELECT p FROM Post p WHERE p.apartment.id = :apartmentId")
    Page<Post> findPostByApartmentId(@Param("apartmentId") Long apartmentId, Pageable pageable);

    Page<Post> findByApartment_Building_Id(Long buildingId, Pageable pageable);
    Page<Post> findByApartment_Building_Zone_Id(Long zoneId, Pageable pageable);
    Page<Post> findByApartment_Building_Zone_Area_Id(Long areaId, Pageable pageable);
}
