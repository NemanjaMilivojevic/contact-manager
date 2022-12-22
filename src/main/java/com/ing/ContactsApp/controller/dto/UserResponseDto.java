package com.ing.ContactsApp.controller.dto;

public class UserResponseDto {
    private String fullName;
    private String email;
    private String userRole;

    public UserResponseDto() {
    }

    public UserResponseDto(String fullName, String email, String userRole) {
        this.fullName = fullName;
        this.email = email;
        this.userRole = userRole;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
