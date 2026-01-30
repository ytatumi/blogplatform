package com.example.blogplatform.model.dto;

import com.example.blogplatform.model.Role;

public record UserListDTO(String username,
                          String name,
                          Role role
) {}
