package com.example.blogplatform.service;
import com.example.blogplatform.model.dto.AdminRegisterRequestDTO;
import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.model.entity.AppUser;

import java.util.List;


public interface UserService {
    List<AppUser> findAllUser();
    AppUser getUserById(Long userId);
    AppUser getUserByUsername(String username);
    AppUser createUser(RegisterRequestDTO registerRequestDTO);
    AppUser createAdmin(AdminRegisterRequestDTO registerRequestDTO);

}
