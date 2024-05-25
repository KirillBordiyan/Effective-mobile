package com.example.effectivemobile2.repo;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum FilterParams {
    @JsonProperty("full_name")
    FULL_NAME,
    @JsonProperty("phone")
    PHONE,
    @JsonProperty("email")
    EMAIL,
    @JsonProperty("birth_date")
    BIRTH_DATE;
}
