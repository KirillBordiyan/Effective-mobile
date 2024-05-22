package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    @ManyToOne
    @JoinColumn(name = "bank_user_id", nullable = true)
    private BankUser bankUser;

    public Email(String email) {
        this.email = email;
    }
}