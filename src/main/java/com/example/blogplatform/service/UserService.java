package com.example.blogplatform.service;
import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.model.entity.AppUser;


public interface UserService {
    AppUser getUserById(Long userId);
    AppUser getUserByUsername(String username);
    AppUser createUser(RegisterRequestDTO registerRequestDTO);


}
