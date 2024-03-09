package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}
