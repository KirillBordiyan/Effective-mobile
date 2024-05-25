package com.example.effectivemobile2.repo;

import com.example.effectivemobile2.entity.BankUser;
import com.example.effectivemobile2.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    Email findByEmail(String email);
}
