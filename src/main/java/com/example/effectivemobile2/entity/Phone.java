package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "phone")
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String phone;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BankUser bank_user;

    public Phone(String phone, BankUser bank_user) {
        this.phone = phone;
        this.bank_user = bank_user;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", phone='" + phone + '\'';
    }
}
