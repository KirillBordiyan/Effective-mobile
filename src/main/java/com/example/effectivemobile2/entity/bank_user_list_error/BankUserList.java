package com.example.effectivemobile2.entity.bank_user_list_error;

import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.example.effectivemobile2.entity.bank_user.BankUserEntity;
import com.example.effectivemobile2.entity.bank_user.BankUserError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
public class BankUserList extends BankUserEntity {
    List<BankUser> bankUserList;
    Integer nextPage;

    public BankUserList(List<BankUserError> bankUserErrors) {
        super();
    }
}
