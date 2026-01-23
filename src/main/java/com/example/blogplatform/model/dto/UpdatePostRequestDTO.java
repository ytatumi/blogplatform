package com.example.blogplatform.model.dto;

import com.example.blogplatform.model.PostStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequestDTO {
    @Size(min=3, max=500, message= "Title must be between {min} and {max} characters")
    private String title;
    @Size(min=3, max=70000, message= "Content must be between {min} and {max} characters")
    private String content;
    private Long categoryId;
    @NotNull(message="Status is required")
    private PostStatus status;

}
