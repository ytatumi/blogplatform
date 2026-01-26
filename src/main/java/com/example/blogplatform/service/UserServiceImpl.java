package com.example.blogplatform.service;

import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser getUserById(Long userId) {
        return appUserRepository
                .findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("User not found with id: " + userId));
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return appUserRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with id: " + username));
    }

    @Override
    public AppUser createUser(RegisterRequestDTO registerRequestDTO) {
        Set<String> userRoles = new HashSet<>();
        userRoles.add("USER");
        AppUser user = AppUser.builder()
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .name(registerRequestDTO.getName())
                .roles(userRoles)
                .build();
        return appUserRepository.save(user);
    }


}
