package com.example.effectivemobile2.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.annotation.ElementType;
import java.time.LocalDate;
import java.util.*;

@Entity(name = "bank_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankUser extends BankUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    @PositiveOrZero(message = "Balance should be greater or equals 0")
    @Column(name = "current_balance")
    private float currentBalance;

    @OneToMany(mappedBy = "bank_user",cascade = CascadeType.ALL)
    private Set<Phone> phoneList;

    @OneToMany(mappedBy = "bank_user", cascade = CascadeType.ALL)
    private Set<Email> emailList;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Size(min = 2, max = 40, message = "Full name cannot be shorter than 2 and longer 40 symbols")
    @Column(name = "full_name", nullable = false)
    private String fullName;
}
