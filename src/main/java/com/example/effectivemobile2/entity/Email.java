package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity(name = "email")
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BankUser bank_user;

    public Email(String email, BankUser bank_user) {
        this.email = email;
        this.bank_user = bank_user;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", email='" + email + '\'';
    }
}