package com.example.blogplatform.controller;

import com.example.blogplatform.config.JwtAuthUtil;
import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthUtil jwtAuthUtil;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<AppUser>> GetAllUser()
    {
        return ResponseEntity.ok(userService.findAllUser());
    }

    @PostMapping
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequestDTO requestDTO) {
        userService.createAdmin(requestDTO);
        return ResponseEntity.ok("Admin is registered successfully");
    }





}
