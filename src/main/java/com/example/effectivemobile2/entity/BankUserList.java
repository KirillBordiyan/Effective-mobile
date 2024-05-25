package com.example.effectivemobile2.entity;

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
}
