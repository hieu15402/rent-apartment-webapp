package com.example.vinhomeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notifications extends  Base{
    private String context;
    private LocalTime time;
    private String tittle;
    private boolean isRead;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "notification_notification_type",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_type_id")
    )
    private Set<NotificationType> notificationTypes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users users;
}
