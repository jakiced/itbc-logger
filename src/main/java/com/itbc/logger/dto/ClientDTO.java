package com.itbc.logger.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class ClientDTO {
    @Email
    private String email;

    @Length(min = 3, message = "Username must be at least 3 characters long")
    private String username;

    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
