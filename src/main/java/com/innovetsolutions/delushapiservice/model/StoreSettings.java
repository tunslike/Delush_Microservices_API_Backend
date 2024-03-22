package com.innovetsolutions.delushapiservice.model;

import lombok.Data;

@Data
public class StoreSettings {
    private Integer bulk_discount;
    private String payment_method;
    private Double delivery_fee;
    private Double delivery_pack;
}
