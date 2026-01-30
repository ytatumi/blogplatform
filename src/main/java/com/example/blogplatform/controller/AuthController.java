package com.example.blogplatform.controller;

import com.example.blogplatform.config.CustomUserDetailService;
import com.example.blogplatform.config.JwtAuthUtil;
import com.example.blogplatform.model.dto.LoginRequestDTO;
import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final CustomUserDetailService UserdetailService;


    @PostMapping("/users")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO requestDTO) {
        userService.createUser(requestDTO);
        return ResponseEntity.ok("User is registered successfully");
    }

    /*
    @PostMapping("/adminregister")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequestDTO requestDTO) {
        userService.createAdmin(requestDTO);
        return ResponseEntity.ok("Admin is registered");
    } */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails user = UserdetailService.loadUserByUsername(request.getUsername());


        String jwt = jwtAuthUtil.generateToken(user);

        return ResponseEntity.ok(jwt);
    }

}
