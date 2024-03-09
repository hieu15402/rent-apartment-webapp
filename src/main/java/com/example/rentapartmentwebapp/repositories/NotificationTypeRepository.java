package com.example.rentapartmentwebapp.repositories;

import com.example.rentapartmentwebapp.models.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType,Long> {
    @Override
    List<NotificationType> findAll();
    NotificationType findNotificationTypeById(Long id);

    NotificationType save(NotificationType payment);

}
