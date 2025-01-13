package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.PaymentDTO;
import com.example.vinhomeproject.models.Contract;
import com.example.vinhomeproject.models.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDto(Payment payment);

    default Long mapContractId(Contract contract) {
        return contract != null ? contract.getId() : null;
    }
}
