package com.innovetsolutions.delushapiservice.dto;

import com.innovetsolutions.delushapiservice.model.Entry;
import lombok.Data;

import java.util.List;

@Data
public class CustomerOrderDto {
    private String customerID;
    private List<OrderItemDto> order;
}
