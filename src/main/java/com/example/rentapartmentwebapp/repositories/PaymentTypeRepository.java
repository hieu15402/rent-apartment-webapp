package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.PaymentType;
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
