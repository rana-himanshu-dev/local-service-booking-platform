package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.AuthResponse;
import com.localservice.bookingplatform.dto.LoginRequest;
import com.localservice.bookingplatform.dto.RegisterRequest;
import com.localservice.bookingplatform.enums.Role;
import com.localservice.bookingplatform.model.User;
import com.localservice.bookingplatform.repository.UserRepository;
import com.localservice.bookingplatform.config.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    public  AuthResponse register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already Registered " + request.getEmail());
        }
        User user  = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        try{
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        }catch(IllegalArgumentException e){
            throw new RuntimeException("Invalid role "  + request.getRole() + ". Muste be CUSTOMER, PROVIDER, or ADMIN");
        }

        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(
                savedUser.getEmail(),
                savedUser.getRole().name()
        );
        return new AuthResponse("Registration successful",token,savedUser.getEmail(),savedUser.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(
                        "No user found with email: " + request.getEmail()));

        if (!user.getIsActive()) {
            throw new RuntimeException(
                    "Account is deactivated. Contact support.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name());

        return new AuthResponse(
                "Login successful",
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}
