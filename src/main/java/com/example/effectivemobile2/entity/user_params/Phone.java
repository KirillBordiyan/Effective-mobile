package com.example.effectivemobile2.entity.user_params;

import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "phone")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "bank_user" })
public class Phone extends UserParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String number;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BankUser bank_user;

    public Phone(String number, BankUser bank_user) {
        this.number = number;
        this.bank_user = bank_user;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", phone='" + number + "'}";
    }
}