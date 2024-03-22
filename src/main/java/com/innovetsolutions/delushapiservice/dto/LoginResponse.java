package com.innovetsolutions.delushapiservice.dto;

import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.StoreSettings;
import lombok.Data;

@Data
public class LoginResponse {
    private Response response;
    private Entry customer;
    private StoreSettings storeSettings;
}
