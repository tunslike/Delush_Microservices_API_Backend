package com.innovetsolutions.delushapiservice.services;

import com.innovetsolutions.delushapiservice.dto.CreateAccountDto;
import com.innovetsolutions.delushapiservice.dto.FetchCustomerOrderDto;
import com.innovetsolutions.delushapiservice.dto.SignInDto;
import com.innovetsolutions.delushapiservice.dto.UpdateProfileDto;
import com.innovetsolutions.delushapiservice.model.CustomerOrders;
import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.StoreSettings;
import com.innovetsolutions.delushapiservice.repository.CustomerEntry;
import com.innovetsolutions.delushapiservice.rowMappers.CustomerOrdersWrapper;
import com.innovetsolutions.delushapiservice.rowMappers.EntryRowMapper;
import com.innovetsolutions.delushapiservice.rowMappers.StoreSettingsWrapper;
import com.innovetsolutions.delushapiservice.utilities.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerEntryService implements CustomerEntry<Entry> {

    private static final String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/DelushDB";
    private static final String dbUsername = "root";
    private static final String dbPassword = "";

    //LOGGER
    private static final Logger logger = LoggerFactory.getLogger(CustomerEntryService.class);
    private static DataSource dataSource;

    public CustomerEntryService() {
        dataSource = Utilities.initialDataSource(driverClassName, dbUrl, dbUsername, dbPassword);
    }

    @Override
    public boolean CreateCustomerAccount(CreateAccountDto account) throws Exception {
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        try {

            //SQL Script
            String sql = "INSERT INTO Delush_ENTRY (CUSTOMER_ID, USERNAME, FIRST_NAME, LAST_NAME, " +
                    "MOBILE_PHONE, EMAIL_ADDRESS, CREATED_DATE) VALUES " +
                    "(?,?,?,?,?,?,?)";

            String Customer_ID = UUID.randomUUID().toString();

            //insert
            int status = dbCor.update(sql, Customer_ID, account.getPhoneNumber(), account.getFirstname(), account.getLastname(),
                    account.getPhoneNumber(), account.getEmailAddress(), LocalDateTime.now());

            if(status == 1) {
                //create password
                createClientPINSecret(Customer_ID, account.getPassword());
                logger.info("New Customer Created Successfully");
                return true;
            }else{
                return false;
            }

        }catch(Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Entry AuthenticateCustomerAccount(SignInDto account) {

        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        Entry customerEntry = null;

        try {

            //SQL Script
            String sql = "SELECT * FROM Delush_ENTRY WHERE USERNAME = ?";

            //spool;
            customerEntry = dbCor.queryForObject(sql, new Object[]{account.getUsername()}, new EntryRowMapper());

            if(customerEntry != null) {

                //validate passcode
                if(ValidateCustomerEntryCode(customerEntry.getCUSTOMER_ENTRY_ID(), account.getPassword())) {

                    return customerEntry;
                }else{
                    return customerEntry;
                }
            }

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public StoreSettings FetchStoreSettings() {
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        StoreSettings settings = new StoreSettings();

        try {

            //SQL Script
            String sql = "SELECT (SELECT SETUP_VALUE FROM Delush_STORE_SETUP WHERE " +
                    "SETUP_NAME = 'BULK DISCOUNT') AS BULK_DISCOUNT, " +
                    "(SELECT SETUP_VALUE FROM Delush_STORE_SETUP WHERE SETUP_NAME = " +
                    "'PAYMENT METHOD') AS PAYMENT_METHOD, (SELECT SETUP_VALUE FROM " +
                    "Delush_STORE_SETUP WHERE SETUP_NAME = 'DELIVERY FEE') AS DELIVERY_FEE, " +
                    "(SELECT SETUP_VALUE FROM Delush_STORE_SETUP WHERE SETUP_NAME = " +
                    "'DELIVERY PACK') AS DELIVERY_PACK;";

            //spool;
            settings = dbCor.queryForObject(sql, new StoreSettingsWrapper());

            return settings;

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Entry ValidateCustomerID(FetchCustomerOrderDto customer) {
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        Entry customerEntry = null;

        try {

            //SQL Script
            String sql = "SELECT * FROM Delush_ENTRY WHERE CUSTOMER_ID = ?";

            //spool;
            customerEntry = dbCor.queryForObject(sql, new Object[]{customer.getCustomer_id()}, new EntryRowMapper());

            return customerEntry;

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Entry fetchCustomerProfile(FetchCustomerOrderDto customer) throws Exception {

        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        Entry profile = null;

        try {

            //SQL Script
            String sql = "SELECT CUSTOMER_ID, USERNAME, FIRST_NAME, LAST_NAME, MOBILE_PHONE, " +
                    "EMAIL_ADDRESS FROM Delush_ENTRY WHERE CUSTOMER_ID = ?";

            profile = dbCor.queryForObject(sql, new Object[] { customer.getCustomer_id() }, new EntryRowMapper());

            return profile;

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean UpdateCustomerProfile(UpdateProfileDto profile) throws Exception {

        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        try {

            //SQL Script
            String sql = "UPDATE Delush_ENTRY SET USERNAME = ?, MOBILE_PHONE = ?, " +
                        "EMAIL_ADDRESS = ?, DATE_UPDATED = ?, UPDATED_BY = ? WHERE CUSTOMER_ID = ?";

            //insert
            int status = dbCor.update(sql, profile.getPhoneNumber(), profile.getPhoneNumber(),
                    profile.getEmailAddress(), LocalDateTime.now(), profile.getCustomerID(), profile.getCustomerID());

            if(status == 1) {
                logger.info("Customer profile update!");
                return true;
            }else {
                return false;
            }

        }catch(Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    private boolean ValidateCustomerEntryCode(String CustomerEntryID, String plainPassword) {
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        try {

            //SQL Script
            String sql = "SELECT ACCESS_CODE FROM Delush_ACCESS WHERE CUSTOMER_ENTRY_ID = ?";

            //spool;
            String hashPassword = (String) dbCor.queryForObject(sql, new Object[] { CustomerEntryID }, String.class);

            return Utilities.validatePINNumber(plainPassword, hashPassword);

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    // create customer PIN access
    private boolean createClientPINSecret(String CustomerID, String PinNumber) {

        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        try {

            //SQL Script
            String sql = "INSERT INTO Delush_ACCESS (ACCESS_ID, CUSTOMER_ENTRY_ID, ACCESS_CODE, " +
                    "DATE_CREATED) VALUES (?, ?, ?, ?)";

            String ACCESS_ID = UUID.randomUUID().toString();
            String CUSTOMER_ID = CustomerID;
            String CUSTOMER_ACCESS_CODE = Utilities.hashPINSecret(PinNumber);

            //insert
            int status = dbCor.update(sql, ACCESS_ID, CUSTOMER_ID, CUSTOMER_ACCESS_CODE, LocalDateTime.now());

            if(status == 1) {
                logger.info("PIN Password Created Successfully");
                return true;
            }else {
                return false;
            }

        }catch(Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }
    // end of service
}
