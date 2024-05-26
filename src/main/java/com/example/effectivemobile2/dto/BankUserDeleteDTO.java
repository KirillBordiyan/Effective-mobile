package com.example.effectivemobile2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankUserDeleteDTO {
    @NotBlank
    private Long id;
    private String phoneDelete;
    private String emailDelete;
}
