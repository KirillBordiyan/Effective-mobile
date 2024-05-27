package com.example.effectivemobile2.entity.user_params;

import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "email")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "bank_user" })
public class Email extends UserParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String mail;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BankUser bank_user;

    public Email(String mail, BankUser bank_user) {
        this.mail = mail;
        this.bank_user = bank_user;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", email='" + mail + "}";
    }
}
