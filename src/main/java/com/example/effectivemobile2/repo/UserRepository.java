package com.example.effectivemobile2.repo;

import com.example.effectivemobile2.entity.BankUser;
import com.example.effectivemobile2.entity.Email;
import com.example.effectivemobile2.entity.Phone;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<BankUser, Long> {
    Page<BankUser> findByBirthDateGreaterThanEqual (LocalDate birtDate, Pageable pageable); //фильтр, где дата > чем в запросе
    Page<BankUser> findByPhones (Phone phone, Pageable pageable); // фильтр по 100% сходству
    Page<BankUser> findByFullNameContaining (String name, Pageable pageable);//фильтр по совпадению like ‘{text-from-request-param}%’
    Page<BankUser> findByEmails (Email email, Pageable pageable); // фильтр по 100% сходству
}
