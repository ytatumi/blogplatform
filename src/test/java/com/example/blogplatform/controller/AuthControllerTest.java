package com.example.blogplatform.controller;

import com.example.blogplatform.config.CustomUserDetailService;
import com.example.blogplatform.config.JwtAuthUtil;
import com.example.blogplatform.model.dto.LoginRequestDTO;
import com.example.blogplatform.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtAuthUtil jwtAuthUtil;

    @MockitoBean
    private CustomUserDetailService userDetailService;

    @Test
    void register_shouldReturn200()throws Exception{
        String registerRequest = """
                {
                "username": "test",
                "password": "password",
                "name": "test user"
                }
                """;
        mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("User is registered successfully"));

        verify(userService).createUser(any());

    }


    @Test
    void login_shouldReturnJwt()throws Exception{
        LoginRequestDTO requestDTO = new LoginRequestDTO("test", "password");

        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);

        when(userDetailService.loadUserByUsername("test")).thenReturn(userDetails);

        when(jwtAuthUtil.generateToken(userDetails)).thenReturn("test_jwt");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "test",
                            "password": "password"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("test_jwt"));


    }






}
