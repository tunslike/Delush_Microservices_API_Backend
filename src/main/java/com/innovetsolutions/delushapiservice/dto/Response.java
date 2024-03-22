package com.innovetsolutions.delushapiservice.dto;

import lombok.Data;

@Data
public class Response {
    private Integer ResponseCode;
    private String ResponseMessage;
    private String ExceptionMessage;
}
