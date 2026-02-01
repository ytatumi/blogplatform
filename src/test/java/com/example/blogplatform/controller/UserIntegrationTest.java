package com.example.blogplatform.controller;

import com.example.blogplatform.model.Role;
import com.example.blogplatform.model.entity.AppUser;
import com.example.blogplatform.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UserIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    void LoginShouldReturnUserToken() throws Exception {
        AppUser appUser = AppUser.builder()
                .username("testuser")
                .password(passwordEncoder.encode("testuserpassword123"))
                .name("testuser fullname")
                .roles(Set.of(Role.USER))
                .build();
        appUserRepository.save(appUser);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                           "username": "testuser",
                           "password": "testuserpassword123"
                        }
                        """))
                .andExpect(status().isOk());

    }



}
