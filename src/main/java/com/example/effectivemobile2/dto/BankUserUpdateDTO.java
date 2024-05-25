package com.example.effectivemobile2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankUserUpdateDTO {

    private Long id;
    private String phoneUpdate;
    private String emailUpdate;
}
