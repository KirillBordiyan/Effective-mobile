package com.example.effectivemobile2.entity.bank_user_list_error;

import com.example.effectivemobile2.entity.bank_user.BankUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BankUserListError extends BankUserEntity {
    private String error;
}

