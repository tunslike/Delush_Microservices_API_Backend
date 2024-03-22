package com.innovetsolutions.delushapiservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInDto {

    @NotEmpty(message = "Username is required.")
    private String username;

    @NotBlank(message = "PIN Number is required.")
    private String password;
}
