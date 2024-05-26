package com.example.effectivemobile2.entity.bank_user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BankUserError extends BankUserEntity {
    private String error;
}

