package com.itbc.logger.dto;

import org.hibernate.validator.constraints.Length;

public class PasswordDTO {
    @Length(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
