package com.example.blogplatform.service;

import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.repository.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;

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
}
