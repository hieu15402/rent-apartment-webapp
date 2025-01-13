package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Apartment;
import com.example.vinhomeproject.models.Appointment;
import com.example.vinhomeproject.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query("SELECT u FROM Users u ORDER BY u.id ASC ")
    List<Users> findAll();
    Optional<Users> findByEmail(String email);

    @Query("SELECT a FROM Apartment a, Appointment ap, Contract c, ContractHistory ch, Users u " +
            "WHERE a.id = ap.apartment.id " +
            "AND ap.id = c.appointment.id " +
            "AND c.contractHistory.id = ch.id " +
            "AND ch.users.id = u.id " +
            "AND u.id = ?1 AND ap.statusAppointment like ?2")
    List<Apartment> getListApartmentByUserId(Long id,String status);

    @Query("SELECT u FROM Users u WHERE u.email LIKE %:email%")
    Page<Users> searchByEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.email LIKE %:email%")
    Page<Users> searchByUser(@Param("email") String email, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.users.email like %?1% AND a.statusAppointment like ?2 AND a.contract is null ")
    List<Appointment> searchAppointmentCompleteByEmail(String email,String status);

    @Query("SELECT u, COUNT(c)  " +
            "FROM Users u " +
            "JOIN u.appointments a " +
            "JOIN a.contract c " +
            "WHERE c.statusOfPayment = true " +
            "GROUP BY u " +
            "ORDER BY COUNT(c) DESC")
    List<Object[]> findTop5UsersWithMostPaidContracts();

}
