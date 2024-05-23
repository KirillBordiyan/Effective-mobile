package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "bank_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankUser extends BankUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;
    @Column(name = "login",unique = true)
    private String login;
    @Column(name = "password")
    private String password;
    //    @PositiveOrZero(message = "Balance should be greater or equals 0")
    @Column(name = "current_balance_bank_user")
    private float currentBalance;
    //    @NotBlank(message = "Need min 1 number")
    @OneToMany(mappedBy = "phone", cascade = CascadeType.ALL)
    @Column(name = "phones")
    private Set<Phone> phoneNumberList = new HashSet<>();
    //    @NotBlank(message = "Need min 1 email")
    @OneToMany(mappedBy = "email", cascade = CascadeType.ALL)
    @Column(name = "emails")
//    @ElementCollection(targetClass = Phone.class, fetch = FetchType.EAGER)
    private Set<Email> emailList = new HashSet<>();
    //    @Column(nullable = false) //если в бд null, будет ошибка
    @Column(name = "birth_date")
    private LocalDate birthDate;
    //    @Size(min = 2, max = 40, message = "Full name cannot be shorter than 2 and longer 40 symbols")
    @Column(name = "full_name")
    private String fullName;

}

