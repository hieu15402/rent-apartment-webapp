package com.example.vinhomeproject.mapper;

import com.example.vinhomeproject.dto.UpdateUserDTO;
import com.example.vinhomeproject.dto.UserAppointmentDTO;
import com.example.vinhomeproject.dto.UserDTO;
import com.example.vinhomeproject.dto.UserDTO_2;
import com.example.vinhomeproject.models.Appointment;
import com.example.vinhomeproject.models.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "review", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contractHistories", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "tokens", ignore = true)
    @Mapping(target = "role", ignore = true)
    Users createClassDtoToClassSubject(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "review", ignore = true),
            @Mapping(target = "contractHistories", ignore = true),
            @Mapping(target = "notifications", ignore = true),
            @Mapping(target = "appointments", ignore = true),
            @Mapping(target = "tokens", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "authorities", ignore = true)
    })
    void updateUser(UpdateUserDTO userDTO, @MappingTarget Users users);

    @Mapping(target = "appointments", source = "appointments") // Ánh xạ trường appointments
    UserDTO_2 toUserDTO_2(Users user);

    default Set<Appointment> mapAppointments(Set<Appointment> appointments) {
        return appointments;
    }

    UserAppointmentDTO toUser(Users user);
}
