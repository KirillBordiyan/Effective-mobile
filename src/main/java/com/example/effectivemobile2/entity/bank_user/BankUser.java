package com.example.effectivemobile2.entity.bank_user;

import com.example.effectivemobile2.entity.role.ERole;
import com.example.effectivemobile2.entity.role.Role;
import com.example.effectivemobile2.entity.user_params.Email;
import com.example.effectivemobile2.entity.user_params.Phone;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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

    @Column(name = "password", nullable = false)
    private String password;

    @PositiveOrZero(message = "Balance should be greater or equals 0")
    @Column(name = "current_balance")
    private float currentBalance;

    @OneToMany(mappedBy = "bank_user",cascade = CascadeType.ALL)
    private Set<Phone> phones;

    @OneToMany(mappedBy = "bank_user", cascade = CascadeType.ALL)
    private Set<Email> emails;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Size(min = 2, max = 40, message = "Full name cannot be shorter than 2 and longer 40 symbols")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role role;
}
