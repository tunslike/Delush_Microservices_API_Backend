package com.innovetsolutions.delushapiservice.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private String foodMenuID;
    private String foodName;
    private Integer quantity;
    private Double foodAmount;
    private Integer bulkOrder;
}
