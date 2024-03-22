package com.innovetsolutions.delushapiservice.repository;

import com.innovetsolutions.delushapiservice.dto.CreateAccountDto;
import com.innovetsolutions.delushapiservice.dto.FetchCustomerOrderDto;
import com.innovetsolutions.delushapiservice.dto.SignInDto;
import com.innovetsolutions.delushapiservice.dto.UpdateProfileDto;
import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.StoreSettings;

public interface CustomerEntry<T> {

    //create new customer
    boolean CreateCustomerAccount(CreateAccountDto account) throws Exception;

    //login customer account
    Entry AuthenticateCustomerAccount(SignInDto account);

    //login customer account
    Entry ValidateCustomerID(FetchCustomerOrderDto customer);

    // fetch customer profile
    Entry fetchCustomerProfile(FetchCustomerOrderDto CustomerID) throws Exception;

    boolean UpdateCustomerProfile(UpdateProfileDto profile) throws Exception;

    StoreSettings FetchStoreSettings() throws Exception;

}
