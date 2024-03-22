package com.innovetsolutions.delushapiservice.repository;

import com.innovetsolutions.delushapiservice.dto.*;
import com.innovetsolutions.delushapiservice.model.CustomerOrders;
import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.OrderDelivery;
import com.innovetsolutions.delushapiservice.model.OrderMenu;

import java.util.List;

public interface CustomerOrder<T> {

    //fetch food menu
    List<OrderMenu> fetchFoodMenu(FetchMenuDto menu) throws Exception;

    String submitCustomerOrder(CustomerOrderDto order) throws Exception;

    List<CustomerOrders> fetchCustomerOrders(FetchCustomerOrderDto CustomerID) throws Exception;

    OrderDelivery fetchOrderDetails(FetchOrderDeliveryDto order) throws Exception;

}
