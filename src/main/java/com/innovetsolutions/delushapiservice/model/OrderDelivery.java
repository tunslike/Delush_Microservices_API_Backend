package com.innovetsolutions.delushapiservice.model;

import lombok.Data;

@Data
public class OrderDelivery {
    private String order_id;
    private String order_number;
    private String order_comments;
    private String delivery_contact_name;
    private String delivery_contact_phone;
    private String delivery_process_date;
    private Integer order_status;
}
