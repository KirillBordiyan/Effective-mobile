package com.example.effectivemobile2.repo;

import com.example.effectivemobile2.entity.BankUser;
import com.example.effectivemobile2.entity.Email;
import com.example.effectivemobile2.entity.Phone;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<BankUser, Long> {
    List<BankUser> findByBirthDateGreaterThanEqual (LocalDate birtDate); //фильтр, где дата > чем в запросе
    List<BankUser> findByPhones (Phone phone); // фильтр по 100% сходству
    List<BankUser> findByFullNameContaining (String name);//фильтр по совпадению like ‘{text-from-request-param}%’
    List<BankUser> findByEmails (Email email); // фильтр по 100% сходству
}


