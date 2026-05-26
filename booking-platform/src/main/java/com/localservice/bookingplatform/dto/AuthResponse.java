package com.localservice.bookingplatform.dto;

public class AuthResponse {
    private String message;
    private String token;
    private String email;
    private String role;
    public AuthResponse(String message, String token, String email, String role) {
        this.message = message;
        this.token = token;
        this.email = email;
        this.role = role;
    }
    public String getMessage(){
        return message;
    }
    public String getToken(){
        return token;
    }
    public String getEmail(){
        return email;
    }
    public String getRole(){
        return role;
    }
}
