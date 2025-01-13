package com.example.vinhomeproject.dto;

import com.example.vinhomeproject.models.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImageUpdateDTO {
    private String image_url;
    private String image_alt;
    private long postId;
}
