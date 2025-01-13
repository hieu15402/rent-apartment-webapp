package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.models.PaymentType;
import com.example.vinhomeproject.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {
    @Override
    List<PaymentType> findAll();
    PaymentType findPaymentTypeById(Long id);

    PaymentType save(PaymentType paymentType);

}
