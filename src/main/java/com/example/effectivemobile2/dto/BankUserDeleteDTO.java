package com.example.effectivemobile2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankUserDeleteDTO {
    @NonNull
    private Long id;
}
