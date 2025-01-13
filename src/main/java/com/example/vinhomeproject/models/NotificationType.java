package com.example.vinhomeproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationType extends Base{
    private String name;
    private boolean status;

    @ManyToMany(mappedBy = "notificationTypes")
    @JsonIgnore
    private Set<Notifications> notifications;
}
