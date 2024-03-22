package com.innovetsolutions.delushapiservice.services;

import com.innovetsolutions.delushapiservice.dto.*;
import com.innovetsolutions.delushapiservice.model.CustomerOrders;
import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.OrderDelivery;
import com.innovetsolutions.delushapiservice.model.OrderMenu;
import com.innovetsolutions.delushapiservice.repository.CustomerOrder;
import com.innovetsolutions.delushapiservice.rowMappers.CustomerOrdersWrapper;
import com.innovetsolutions.delushapiservice.rowMappers.EntryRowMapper;
import com.innovetsolutions.delushapiservice.rowMappers.FetchMenuWrapper;
import com.innovetsolutions.delushapiservice.rowMappers.OrderDeliveryRowMapper;
import com.innovetsolutions.delushapiservice.utilities.Utilities;
import jakarta.validation.constraints.Null;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerOrderService implements CustomerOrder<OrderMenu> {

    private static final String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/DelushDB";
    private static final String dbUsername = "root";
    private static final String dbPassword = "";

    //LOGGER
    private static final Logger logger = LoggerFactory.getLogger(CustomerEntryService.class);
    private static DataSource dataSource;

    public CustomerOrderService () {
        dataSource = Utilities.initialDataSource(driverClassName, dbUrl, dbUsername, dbPassword);
    }

    @Override
    public List<OrderMenu> fetchFoodMenu(FetchMenuDto category) {
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        List<OrderMenu> menu = null;

        try {

            String sql = switch (category.getFoodName()) {
                case "Drink" -> "SELECT FOOD_MENU_ID, CATEGORY_ID, FOOD_NAME, " +
                        "DESCRIPTION, AMOUNT, QUANTITY, FOOD_MENU_IMG, DATE_CREATED FROM Delush_FOOD_MENU " +
                        "WHERE CATEGORY_ID = 'Drink' AND STATUS IN (0,1)";
                case "Snack" -> "SELECT FOOD_MENU_ID, CATEGORY_ID, FOOD_NAME, " +
                        "DESCRIPTION, AMOUNT, QUANTITY, FOOD_MENU_IMG, DATE_CREATED FROM Delush_FOOD_MENU " +
                        "WHERE CATEGORY_ID = 'Snack' AND STATUS IN (0,1)";
                case "Fruit" -> "SELECT FOOD_MENU_ID, CATEGORY_ID, FOOD_NAME, " +
                        "DESCRIPTION, AMOUNT, QUANTITY, FOOD_MENU_IMG, DATE_CREATED FROM Delush_FOOD_MENU " +
                        "WHERE CATEGORY_ID = 'Fruit' AND STATUS IN (0,1)";
                default -> "SELECT FOOD_MENU_ID, CATEGORY_ID, FOOD_NAME, " +
                        "DESCRIPTION, AMOUNT, QUANTITY, FOOD_MENU_IMG, DATE_CREATED FROM Delush_FOOD_MENU " +
                        "WHERE CATEGORY_ID = 'Food' AND STATUS IN (0,1)";
            };

            menu = dbCor.query(sql, new FetchMenuWrapper());

            return menu;

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public String submitCustomerOrder(CustomerOrderDto order) throws Exception {
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);
        try {

            //SQL Script
            String sql = "INSERT INTO Delush_ORDERS (ORDER_ID, CUSTOMER_ID, ORDER_NUMBER, TOTAL_AMOUNT, " +
                        "ORDER_ITEMS_COUNT, DATE_CREATED, CREATED_BY) VALUES " +
                    "(?,?,?,?,?,?,?)";

            String Order_ID = UUID.randomUUID().toString();
            String Order_Number = String.valueOf(Utilities.generateOrderNumber());
            Double Total_Price = 0.00;
            Integer Order_Item_Count = order.getOrder().size();
            final Integer[] ORDER_ITEMS_COUNT = {0};

            List<OrderItemDto> items = order.getOrder();

            for(OrderItemDto obj : items) {
                Total_Price += createCustomerOrderItems(obj, Order_ID);
            }

            //insert
            int status = dbCor.update(sql, Order_ID, order.getCustomerID(), Order_Number,
                    Total_Price, Order_Item_Count, LocalDateTime.now(),"SYSTEM");

            if(status == 1) {
                logger.info("Customer Orders Created Successfully");
                return Order_Number;
            }else {
                return null;
            }

        }catch(Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<CustomerOrders> fetchCustomerOrders(FetchCustomerOrderDto customer) throws Exception {

        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        List<CustomerOrders> orders = null;

        try {

            //SQL Script
            String sql = "SELECT ORDER_ID, ORDER_NUMBER, TOTAL_AMOUNT, " +
                    "DATE_CREATED, ORDER_STATUS FROM Delush_ORDERS WHERE " +
                    "ORDER_STATUS IN (0,1) AND CUSTOMER_ID = ?";

            orders = dbCor.query(sql, new Object[] { customer.getCustomer_id() }, new CustomerOrdersWrapper());

            return orders;

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public OrderDelivery fetchOrderDetails(FetchOrderDeliveryDto order) throws Exception {
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);

        OrderDelivery delivery;

        try {

            //SQL Script
            String sql = "SELECT ORDER_ID, ORDER_NUMBER, DELIVERY_CONTACT_NAME, " +
                    "DELIVERY_CONTACT_PHONE, COMMENT, ORDER_STATUS, DATE_PROCESSED FROM Delush_ORDERS " +
                    "WHERE ORDER_ID = ?";

            delivery = dbCor.queryForObject(sql, new Object[]{order.getOrder_id()}, new OrderDeliveryRowMapper());

            return delivery;

        }catch(DataAccessException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    private double createCustomerOrderItems(OrderItemDto orderItems, String Order_ID) throws Exception {
        
        JdbcTemplate dbCor = new JdbcTemplate(dataSource);
        try {

            //SQL Script
            String sql = "INSERT INTO Delush_ORDER_ITEMS (ORDER_ID, ORDER_MENU_ID, " +
                    "FOOD_MENU_ID, AMOUNT, QUANTITY, BULK_ORDER, DATE_CREATED, CREATED_BY) VALUES " +
                    "(?,?,?,?,?,?,?,?)";

            String Order_Menu_ID = UUID.randomUUID().toString();

            //insert
            int status =   dbCor.update(sql, Order_ID, Order_Menu_ID, orderItems.getFoodMenuID(),
                    orderItems.getFoodAmount(), orderItems.getQuantity(), orderItems.getBulkOrder(), LocalDateTime.now(),"SYSTEM");

            logger.info("Order item created successfully!");
            return orderItems.getFoodAmount();

        }catch(Exception e) {
            logger.error(e.getMessage());
        }

        return orderItems.getFoodAmount();
    }
}
