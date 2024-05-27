package com.example.effectivemobile2.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LastElementException extends RuntimeException{
    private String message;
}
