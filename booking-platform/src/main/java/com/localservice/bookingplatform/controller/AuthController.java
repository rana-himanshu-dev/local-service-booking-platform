package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.AuthResponse;
import com.localservice.bookingplatform.dto.RegisterRequest;
import com.localservice.bookingplatform.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {
 private final AuthService authService;
 public AuthController(AuthService authService){
     this.authService = authService;
 }

 @PostMapping("/register")
    public ResponseEntity<AuthResponse> regiser(
            @RequestBody RegisterRequest request) {
     AuthResponse response = authService.register(request);
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(response);
 }


}
