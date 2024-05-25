package com.example.effectivemobile2.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
//спец класс по возврату страниц с пользователями
public class BankUserPage  extends BankUserEntity {
    Page<BankUser> bankUserPage;
}
