package com.example.effectivemobile2.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppError {
    private int status;
    private String message;
    private Date timesTamp;

    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timesTamp = new Date();
    }
}
