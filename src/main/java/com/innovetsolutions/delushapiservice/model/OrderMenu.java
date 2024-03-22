package com.innovetsolutions.delushapiservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderMenu {
    private String FOOD_MENU_ID;
    private String CATEGORY;
    private String FOOD_NAME;
    private String DESCRIPTION;
    private double AMOUNT;
    private int QUANTITY;
    private String IMAGE_BASE_64;
    private String DATE_CREATED;
}
