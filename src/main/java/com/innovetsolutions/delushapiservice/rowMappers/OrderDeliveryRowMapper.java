package com.innovetsolutions.delushapiservice.rowMappers;

import com.innovetsolutions.delushapiservice.model.OrderDelivery;
import com.innovetsolutions.delushapiservice.model.OrderMenu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDeliveryRowMapper implements RowMapper<OrderDelivery> {

    @Override
    public OrderDelivery mapRow(ResultSet rs, int rowNum) throws SQLException {

        OrderDelivery delivery = new OrderDelivery();

        delivery.setOrder_id(rs.getString("ORDER_ID"));
        delivery.setOrder_number(rs.getString("ORDER_NUMBER"));
        delivery.setDelivery_contact_name(rs.getString("DELIVERY_CONTACT_NAME"));
        delivery.setDelivery_contact_phone(rs.getString("DELIVERY_CONTACT_PHONE"));
        delivery.setOrder_comments(rs.getString("COMMENT"));
        delivery.setOrder_status(rs.getInt("ORDER_STATUS"));
        delivery.setDelivery_process_date(String.valueOf(rs.getTimestamp("DATE_PROCESSED").toLocalDateTime()));

        return delivery;
    }

}
