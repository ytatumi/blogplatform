package com.example.blogplatform.model.dto;

import com.example.blogplatform.model.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long postId;
    private String comment;
    private String user;
    private LocalDateTime createdAt;

}
