package com.innovetsolutions.delushapiservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerOrders {
    private String ORDER_ID;
    private String ORDER_NUMBER;
    private String AMOUNT;
    private String ORDER_STATUS;
    private String DATE_CREATED;
}
