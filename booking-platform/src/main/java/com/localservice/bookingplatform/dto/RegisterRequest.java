package com.localservice.bookingplatform.dto;

public class RegisterRequest {
    private String fullname;
    private String email;
    private String password;
    private String phone;
    private String role;

    public String getFullName(){return fullname;}
    public void setFullName(String fullname){this.fullname=fullname;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone=phone;}
    public String getRole(){return role;}
    public void setRole(String role){this.role=role;}

}
