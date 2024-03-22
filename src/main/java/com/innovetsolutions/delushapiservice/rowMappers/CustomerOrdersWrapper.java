package com.innovetsolutions.delushapiservice.rowMappers;

import com.innovetsolutions.delushapiservice.model.CustomerOrders;
import com.innovetsolutions.delushapiservice.model.OrderMenu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerOrdersWrapper implements RowMapper {
    @Override
    public CustomerOrders mapRow(ResultSet rs, int rowNum) throws SQLException {

        CustomerOrders orders = new CustomerOrders();

        orders.setORDER_ID(rs.getString("ORDER_ID"));
        orders.setORDER_NUMBER(rs.getString("ORDER_NUMBER"));
        orders.setAMOUNT(rs.getString("TOTAL_AMOUNT"));
        orders.setORDER_STATUS(rs.getString("ORDER_STATUS"));
        orders.setDATE_CREATED(String.valueOf(rs.getTimestamp("DATE_CREATED").toLocalDateTime()));

        return orders;
    }
}
