package com.example.blogplatform.service;
import com.example.blogplatform.model.entity.AppUser;


public interface UserService {
    AppUser getUserById(Long userId);
}
