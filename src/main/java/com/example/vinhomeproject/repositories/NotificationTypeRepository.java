package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.NotificationType;
import com.example.vinhomeproject.models.Payment;
import com.example.vinhomeproject.models.Users;
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
