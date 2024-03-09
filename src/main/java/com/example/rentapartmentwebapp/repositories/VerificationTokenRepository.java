package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Users;
import com.example.rentapartmentwebapp.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUsers(Users users);
}
