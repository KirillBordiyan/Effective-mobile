package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    //    @PositiveOrZero(message = "Balance should be greater or equals 0")
    @Column(name = "current_balance_bank_user")
    private float currentBalance;


    @OneToMany(mappedBy = "bank_user",cascade = CascadeType.ALL)
    @Column(name = "phones")
    private Set<Phone> phoneNumberList;

    @OneToMany(mappedBy = "bank_user", cascade = CascadeType.ALL)
    @Column(name = "emails", columnDefinition = "set<email>")
    private Set<Email> emailList;

    //    @Column(nullable = false) //если в бд null, будет ошибка
    @Column(name = "birth_date")
    private LocalDate birthDate;

    //    @Size(min = 2, max = 40, message = "Full name cannot be shorter than 2 and longer 40 symbols")
    @Column(name = "full_name")
    private String fullName;

}


//    проба без создания репо для обоих списков
//phone
//    @ElementCollection(targetClass = Phone.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "phone", joinColumns = @JoinColumn(name = "user_id"))
//email
//  @ElementCollection(targetClass = Email.class, fetch = FetchType.EAGER)
//  @CollectionTable(name = "email", joinColumns = @JoinColumn(name = "user_id"))
