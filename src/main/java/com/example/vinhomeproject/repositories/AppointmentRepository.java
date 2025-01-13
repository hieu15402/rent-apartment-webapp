package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.dto.AppointmentDTO_2;
import com.example.vinhomeproject.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    @Query("SELECT a FROM Appointment a WHERE a.users.id = :userId")
    List<Appointment> findByUserId(@Param("userId") Long userId);

    @Query("SELECT NEW com.example.vinhomeproject.dto.AppointmentDTO_2(a.statusAppointment,a.dateTime,a.time,a.apartment,a.note,'Q9,TP.HCM',u.address,u.phone,u.fullName,u.image)  FROM Appointment a JOIN  Users u ON a.createBy = u.email WHERE a.users.id = :userId")
    List<AppointmentDTO_2> findByUserIdNew(@Param("userId") Long userId);

}
