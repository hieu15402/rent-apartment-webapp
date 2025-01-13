package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.ContractHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractHistoryRepository extends JpaRepository<ContractHistory,Long> {
}
