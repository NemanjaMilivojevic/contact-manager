package com.ing.ContactsApp.controller.dto;

import com.ing.ContactsApp.domain.entity.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserRequestDto {
    private String fullName;
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[!@#$%^&+=])"
            + "(?=\\S+$).{8,20}$")
    private String password;
    private UserRole userRole;


    public UserRequestDto(String fullName, String email, String password, UserRole userRole) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
