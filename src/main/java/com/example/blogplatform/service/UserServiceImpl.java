package com.example.blogplatform.service;

import com.example.blogplatform.model.Role;
import com.example.blogplatform.model.dto.AdminRegisterRequestDTO;
import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.model.dto.UserListDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.repository.AppUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AppUser> findAllUser() {
        return appUserRepository.findAll();
    }

    @Override
    public List<UserListDTO> findAllUserList() {
        return appUserRepository.findAllUserList();
    }

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
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(Role.USER);
        if (appUserRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()){
           throw new EntityExistsException("User already exists!");
        }
        AppUser user = AppUser.builder()
                .username(registerRequestDTO.getUsername())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .name(registerRequestDTO.getName())
                .roles(userRoles)
                .build();
        return appUserRepository.save(user);
    }

    @Override
    @Transactional
    public AppUser createAdmin(AdminRegisterRequestDTO registerRequestDTO) {
        AppUser user= appUserRepository.findByUsername(registerRequestDTO.getUsername())
                .orElseThrow(()->new EntityNotFoundException("User not found: " + registerRequestDTO.getUsername()));

        if(user.getRoles().contains(Role.ADMIN)) {
            throw new EntityExistsException("The user is already registered as Admin!");
        }

        user.getRoles().add(Role.ADMIN);
        return appUserRepository.save(user);

    }



}
