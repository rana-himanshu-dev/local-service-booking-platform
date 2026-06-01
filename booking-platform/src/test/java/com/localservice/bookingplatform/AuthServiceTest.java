package com.localservice.bookingplatform;

import com.localservice.bookingplatform.config.JwtUtil;
import com.localservice.bookingplatform.dto.AuthResponse;
import com.localservice.bookingplatform.dto.LoginRequest;
import com.localservice.bookingplatform.dto.RegisterRequest;
import com.localservice.bookingplatform.enums.Role;
import com.localservice.bookingplatform.model.User;
import com.localservice.bookingplatform.repository.UserRepository;
import com.localservice.bookingplatform.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setFullName("Test User");
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("encodedPassword");
        testUser.setPhone("9999999999");
        testUser.setRole(Role.CUSTOMER);
        testUser.setIsActive(true);

        registerRequest = new RegisterRequest();
        registerRequest.setFullName("Test User");
        registerRequest.setEmail("test@gmail.com");
        registerRequest.setPassword("test123");
        registerRequest.setPhone("9999999999");
        registerRequest.setRole("CUSTOMER");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("test123");
    }

    @Test
    void register_ShouldReturnToken_WhenValidRequest() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("testToken");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("test@gmail.com", response.getEmail());
        assertEquals("CUSTOMER", response.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString()))
                .thenReturn(true);

        assertThrows(RuntimeException.class, () ->
                authService.register(registerRequest));

        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    void login_ShouldReturnToken_WhenValidCredentials() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("testToken");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
        assertEquals("test@gmail.com", response.getEmail());
    }

    @Test
    void login_ShouldThrowException_WhenWrongPassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.login(loginRequest));

        assertNotNull(exception.getMessage());
    }


    @Test
    void login_ShouldThrowException_WhenEmailNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.login(loginRequest));

        assertNotNull(exception.getMessage());
    }
}