package com.example.effectivemobile2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String phone;
    @ManyToOne
    @JoinColumn(name = "bank_user_id", nullable = true)
    private BankUser bankUser;

    public Phone(String phone) {
        this.phone = phone;
    }
}
