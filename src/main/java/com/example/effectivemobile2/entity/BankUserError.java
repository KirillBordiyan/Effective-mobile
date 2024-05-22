package com.example.effectivemobile2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BankUserError extends BankUserEntity {
    private String error;
}

