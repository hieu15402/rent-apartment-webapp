package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Users;
import com.example.vinhomeproject.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUsers(Users users);
}
