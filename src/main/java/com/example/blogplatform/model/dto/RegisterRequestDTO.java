package com.example.blogplatform.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message="username is required")
    private String username;
    @NotBlank(message="password is required")
    private String password;
    @NotBlank(message="name is required")
    private String name;

}
