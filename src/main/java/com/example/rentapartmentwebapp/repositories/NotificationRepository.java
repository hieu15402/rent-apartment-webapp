package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,Long> {
}
