package com.example.vinhomeproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Bank extends Base{
    private String bankCode;
    private String url_img;
}
