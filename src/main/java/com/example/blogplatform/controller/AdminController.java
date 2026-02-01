package com.example.blogplatform.controller;

import com.example.blogplatform.model.dto.AdminRegisterRequestDTO;
import com.example.blogplatform.model.dto.UserListDTO;
import com.example.blogplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserListDTO>> GetAllUser()
    {
        return ResponseEntity.ok(userService.findAllUserList());
    }

    @PostMapping
    public ResponseEntity<?> createAdmin(@RequestBody AdminRegisterRequestDTO requestDTO) {
        userService.createAdmin(requestDTO);
        return ResponseEntity.ok("Admin is registered successfully");
    }





}
