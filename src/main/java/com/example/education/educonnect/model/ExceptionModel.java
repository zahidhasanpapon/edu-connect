package com.example.education.educonnect.model;

import lombok.Data;

@Data
public class ExceptionModel {
    private int status;
    private String error;
    private String message;
}
