package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
    private BankUser bankUser;

    public Phone(String phone, BankUser bankUser) {
        this.phone = phone;
        this.bankUser = bankUser;
    }
}
