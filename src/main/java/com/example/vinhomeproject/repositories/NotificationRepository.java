package com.example.vinhomeproject.repositories;

import com.example.vinhomeproject.models.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications,Long> {
}
