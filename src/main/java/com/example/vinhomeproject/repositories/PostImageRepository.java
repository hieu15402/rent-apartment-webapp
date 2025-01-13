package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.PostImage;
import com.example.vinhomeproject.models.Users;
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
