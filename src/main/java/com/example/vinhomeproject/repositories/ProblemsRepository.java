package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Problems;
import com.example.vinhomeproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemsRepository extends JpaRepository<Problems,Long> {
}
