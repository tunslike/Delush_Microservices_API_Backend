package com.innovetsolutions.delushapiservice.rowMappers;

import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.OrderMenu;
import org.hibernate.query.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchMenuWrapper implements RowMapper<OrderMenu> {
    @Override
    public OrderMenu mapRow(ResultSet rs, int rowNum) throws SQLException {

        OrderMenu menu = new OrderMenu();

        menu.setFOOD_MENU_ID(rs.getString("FOOD_MENU_ID"));
        menu.setCATEGORY(rs.getString("CATEGORY_ID"));
        menu.setFOOD_NAME(rs.getString("FOOD_NAME"));
        menu.setDESCRIPTION(rs.getString("DESCRIPTION"));
        menu.setAMOUNT(Double.parseDouble(rs.getString("AMOUNT")));
        menu.setQUANTITY(Integer.parseInt(rs.getString("QUANTITY")));
        menu.setIMAGE_BASE_64(rs.getString("FOOD_MENU_IMG"));

        return menu;
    }
}
