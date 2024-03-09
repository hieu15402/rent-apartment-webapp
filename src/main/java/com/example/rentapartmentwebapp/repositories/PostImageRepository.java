package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage,Long> {
    @Override
    List<PostImage> findAll();
    PostImage findPostImageById(Long id);

    PostImage save(PostImage postImage);
}
