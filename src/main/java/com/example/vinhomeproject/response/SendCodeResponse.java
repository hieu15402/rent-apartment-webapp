package com.example.vinhomeproject.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendCodeResponse {
    String email;
    String message;
}
