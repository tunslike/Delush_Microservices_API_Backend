package com.innovetsolutions.delushapiservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateAccountDto {

    @NotBlank(message = "The first name is required.")
    private String firstname;

    @NotBlank(message = "The last name is required.")
    private String lastname;

    @NotBlank(message = "The phone number is required.")
    private String phoneNumber;

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email is not a valid email.")
    private String emailAddress;

    @NotBlank(message = "The password is required.")
    private String password;

}
