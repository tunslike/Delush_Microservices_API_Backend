package com.innovetsolutions.delushapiservice.rowMappers;

import com.innovetsolutions.delushapiservice.model.Entry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntryRowMapper implements RowMapper<Entry> {
    @Override
    public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {

        Entry entry = new Entry();

        entry.setCUSTOMER_ENTRY_ID(rs.getString("CUSTOMER_ID"));
        entry.setUSERNAME(rs.getString("USERNAME"));
        entry.setFIRST_NAME(rs.getString("FIRST_NAME"));
        entry.setLAST_NAME(rs.getString("LAST_NAME"));
        entry.setPHONE_NUMBER(rs.getString("MOBILE_PHONE"));
        entry.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));
        return entry;
    }
}
