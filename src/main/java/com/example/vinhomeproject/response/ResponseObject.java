package com.example.vinhomeproject.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    private String message;
    private Object data;
}
