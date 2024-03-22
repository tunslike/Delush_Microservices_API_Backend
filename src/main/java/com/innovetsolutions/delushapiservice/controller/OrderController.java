package com.innovetsolutions.delushapiservice.controller;


import com.innovetsolutions.delushapiservice.dto.*;
import com.innovetsolutions.delushapiservice.model.CustomerOrders;
import com.innovetsolutions.delushapiservice.model.Entry;
import com.innovetsolutions.delushapiservice.model.OrderDelivery;
import com.innovetsolutions.delushapiservice.model.OrderMenu;
import com.innovetsolutions.delushapiservice.services.CustomerOrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {

    private final CustomerOrderService orderService;

    // service to fetch order delivery details
    @PostMapping("/fetchOrderDeliveryDetails")
    public ResponseEntity fetchOrderDelivery(@Valid @RequestBody FetchOrderDeliveryDto order ) throws Exception {

        OrderDelivery delivery = new OrderDelivery();

        delivery = orderService.fetchOrderDetails(order);

        if(delivery != null) {

            Response response = new Response();

            response.setResponseCode(200);
            response.setResponseMessage("Customer Details Retrieved!");

            return new ResponseEntity(delivery, HttpStatus.OK);
        }

        return new ResponseEntity(delivery, HttpStatus.BAD_REQUEST);
    }// end of service
    // end of service

    // service to fetch food menu
    @PostMapping("/fetchFoodMenu")
    public List<OrderMenu> getFoodMenu(@Valid @RequestBody FetchMenuDto category) throws Exception {

        List<OrderMenu> menu = orderService.fetchFoodMenu(category);

        return menu;
    }
    // end of service

    // service to fetch customer orders
    @PostMapping("/fetchCustomerOrder")
    public List<CustomerOrders> getCustomerOrders(@Valid @RequestBody FetchCustomerOrderDto CustomerID) throws Exception {
        return orderService.fetchCustomerOrders(CustomerID);
    }
    // end of service

    // service to submit customer order
    @PostMapping("/submitCustomerOrder")
    public ResponseEntity submit(@Valid @RequestBody CustomerOrderDto orders) throws Exception {

        String OrderNumber = orderService.submitCustomerOrder(orders);


        if(OrderNumber != null) {

            OrderResponse orderDetails = new OrderResponse();

            Response response = new Response();
            response.setResponseCode(200);
            response.setResponseMessage("Customer order submitted  successfully");

            orderDetails.setOrderNumber(OrderNumber);
            orderDetails.setResponse(response);

            return new ResponseEntity(orderDetails, HttpStatus.OK);

        }else {
            Response response = new Response();
            response.setResponseCode(404);
            response.setResponseMessage("Unable to process your request");

            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

    }
    // end of service

}
