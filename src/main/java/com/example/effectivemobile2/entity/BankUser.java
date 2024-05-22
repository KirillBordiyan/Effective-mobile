package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankUser extends BankUserEntity {

    //пароль логин сюда впихнуть
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;
    private String login;
    private String password;
    //    @PositiveOrZero(message = "Balance should be greater or equals 0")
    private float currentBalance;
    //    @NotBlank(message = "Need min 1 number")
    @OneToMany(mappedBy = "bankUser", cascade = CascadeType.ALL)
    private Set<Phone> phoneNumberList = new HashSet<>();
    //    @NotBlank(message = "Need min 1 email")
    @OneToMany(mappedBy = "bankUser", cascade = CascadeType.ALL)
    private Set<Email> emailList = new HashSet<>();
    //    @Column(nullable = false) //если в бд null, будет ошибка
    private LocalDate birthDate;
    //    @Size(min = 2, max = 40, message = "Full name cannot be shorter than 2 and longer 40 symbols")
    private String fullName;


//    public BankUser(String login, String password, float currentBalance, LocalDate birthDate, String fullName) {
//        this.login = login;
//        this.password = password;
//        this.currentBalance = currentBalance;
//        this.birthDate = birthDate;
//        this.fullName = fullName;
//    }
}

