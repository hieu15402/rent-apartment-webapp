package com.example.vinhomeproject.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendCodeRequest {
    String email;
}
