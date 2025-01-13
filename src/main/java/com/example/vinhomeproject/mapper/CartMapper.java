package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.CartDTO;
import com.example.vinhomeproject.dto.CartDTO_2;
import com.example.vinhomeproject.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateCart(CartDTO cartDTO, @MappingTarget Cart cart);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Cart createCart(CartDTO cartDTO);
    CartDTO_2 cartDtoFromCart(Cart cart);
}
