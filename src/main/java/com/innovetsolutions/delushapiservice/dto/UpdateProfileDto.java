package com.innovetsolutions.delushapiservice.dto;

import lombok.Data;

@Data
public class UpdateProfileDto {
    private String customerID;
    private String phoneNumber;
    private String emailAddress;
}
