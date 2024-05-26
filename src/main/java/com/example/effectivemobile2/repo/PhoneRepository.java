package com.example.effectivemobile2.repo;

import com.example.effectivemobile2.entity.user_params.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Phone findByPhone(String phone);
    Phone deleteByPhone(String  phone);
}

