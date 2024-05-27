package com.example.effectivemobile2.entity.user_params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserParamError extends UserParam{
    private String error;
}
