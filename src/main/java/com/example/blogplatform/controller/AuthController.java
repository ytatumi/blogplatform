package com.example.blogplatform.controller;

import com.example.blogplatform.config.JwtAuthUtil;
import com.example.blogplatform.model.dto.LoginRequestDTO;
import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.repository.AppUserRepository;
import com.example.blogplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthUtil jwtAuthUtil;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO requestDTO) {
        userService.createUser(requestDTO);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        AppUser user = userService.getUserByUsername(request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String jwt = jwtAuthUtil.generateToken(user.getUsername(),"USER");

        return ResponseEntity.ok(jwt);
    }

}
