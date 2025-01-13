package com.example.vinhomeproject.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    String newPassword;
    String confirmPassword;
    String email;
}
