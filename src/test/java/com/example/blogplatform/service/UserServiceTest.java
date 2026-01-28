package com.example.blogplatform.service;

import com.example.blogplatform.model.dto.RegisterRequestDTO;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.repository.AppUserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    AppUserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    RegisterRequestDTO dto;

    @BeforeEach
    void setUp() {
        dto = RegisterRequestDTO.builder()
                .username("testuser1")
                .password("testuser1Password")
                .name("testuser1 fullname")
                .build();
    }

    @Test
    @DisplayName("createUser should return User")
    void createUser_shouldCreateUser() {

        // Arrange
        when(userRepository.findByUsername("testuser1"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("testuser1Password"))
                .thenReturn("EncodedPassword");
        when(userRepository.save(any(AppUser.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AppUser createdUser = userService.createUser(dto);

        // Assert
        assertEquals("testuser1", createdUser.getUsername());
        assertEquals("EncodedPassword", createdUser.getPassword());

        verify(passwordEncoder).encode("testuser1Password");
        verify(userRepository).save(any(AppUser.class));

    }

    @Test
    @DisplayName("createUser should throw exception if user exists")
    void createUser_shouldThrowExceptionIfUserExists() {

        when(userRepository.findByUsername("testuser1"))
                .thenReturn(Optional.of(new AppUser()));

        assertThrows(EntityExistsException.class,()-> userService.createUser(dto));

    }

}
