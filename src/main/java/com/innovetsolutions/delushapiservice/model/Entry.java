package com.innovetsolutions.delushapiservice.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Entry {
    private String CUSTOMER_ENTRY_ID;
    private String USERNAME;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String PHONE_NUMBER;
    private String EMAIL_ADDRESS;
}
