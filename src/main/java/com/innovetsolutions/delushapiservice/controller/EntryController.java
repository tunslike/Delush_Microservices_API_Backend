package com.innovetsolutions.delushapiservice.controller;


import com.innovetsolutions.delushapiservice.dto.*;
import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.StoreSettings;
import com.innovetsolutions.delushapiservice.services.CustomerEntryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class EntryController {

    // inject customer service
    private final CustomerEntryService account;

    // create new customer
    @PostMapping("/newCustomer")
    public ResponseEntity createNewCustomer(@Valid @RequestBody CreateAccountDto customer) throws Exception {

        if(account.CreateCustomerAccount(customer)) {
            Response response = new Response();
            response.setResponseCode(200);
            response.setResponseMessage("Customer created successfully");

            return new ResponseEntity(response, HttpStatus.OK);

        }else {
            Response response = new Response();
            response.setResponseCode(404);
            response.setResponseMessage("Unable to process your request");

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    // end of service

    // service to fetch customer profile
    @PostMapping("/fetchCustomerProfile")
    public ResponseEntity loadCustomerProfile(@Valid @RequestBody FetchCustomerOrderDto customer ) throws Exception {
        Entry customerEntry = new Entry();

        customerEntry = account.fetchCustomerProfile(customer);

        if(customerEntry != null) {

            Response response = new Response();

            response.setResponseCode(200);
            response.setResponseMessage("Customer Details Retrieved!");

            return new ResponseEntity(customerEntry, HttpStatus.OK);
        }

        return new ResponseEntity(customerEntry, HttpStatus.BAD_REQUEST);
    }// end of service

    // service to update customer profile
    @PostMapping("/updateCustomerProfile")
    public ResponseEntity updateCustomerProfile(@Valid @RequestBody UpdateProfileDto profile) throws Exception {
        if(account.UpdateCustomerProfile(profile)) {
            Response response = new Response();
            response.setResponseCode(200);
            response.setResponseMessage("Customer profile updated successfully");

            return new ResponseEntity(response, HttpStatus.OK);

        }else {
            Response response = new Response();
            response.setResponseCode(404);
            response.setResponseMessage("Unable to process your request");

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    // end of service

    // service to validate customer ID
    @PostMapping("/validateCustomerID")
    public ResponseEntity validateCustomerID(@Valid @RequestBody FetchCustomerOrderDto customerID) throws Exception
    {
        Entry customerEntry = new Entry();
        LoginResponse responseEntry = new LoginResponse();

        customerEntry = account.ValidateCustomerID(customerID);

        if(customerEntry != null) {

            Response response = new Response();

            response.setResponseCode(200);
            response.setResponseMessage("Customer Found!");

            responseEntry.setResponse(response);
            responseEntry.setCustomer(customerEntry);

            return new ResponseEntity(responseEntry, HttpStatus.OK);
        }

        return new ResponseEntity(responseEntry, HttpStatus.BAD_REQUEST);
    }
    // end of service

    // service to login
    @PostMapping("/login")
    public ResponseEntity authenticateCustomer(@Valid @RequestBody SignInDto login) {

        Entry customerEntry = new Entry();
        StoreSettings settings = new StoreSettings();
        LoginResponse responseEntry = new LoginResponse();

        customerEntry = account.AuthenticateCustomerAccount(login);
        settings = account.FetchStoreSettings();

        if(customerEntry != null) {

            Response response = new Response();

            response.setResponseCode(200);
            response.setResponseMessage("Customer Found!");

            responseEntry.setResponse(response);
            responseEntry.setCustomer(customerEntry);
            responseEntry.setStoreSettings(settings);

            return new ResponseEntity(responseEntry, HttpStatus.OK);
        }

        return new ResponseEntity(responseEntry, HttpStatus.BAD_REQUEST);
    }

}
