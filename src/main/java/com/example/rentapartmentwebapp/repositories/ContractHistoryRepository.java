package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.ContractHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractHistoryRepository extends JpaRepository<ContractHistory,Long> {
}
