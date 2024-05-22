package com.example.effectivemobile2.service;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
//import javax.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;


    //dto нужен только как набор параметров в коде
    // для создания экземпляра user, который и попадет в БД
    public BankUser create(BankUserCreateDTO dto) {

        BankUser bankUser = BankUser.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .currentBalance(dto.getInitialAmount())
                .birthDate(dto.getBirthDate())
                .fullName(dto.getFullName())
                .build();
        Phone phone = new Phone(dto.getPhoneNumber(), bankUser);
        Email email = new Email(dto.getEmail(), bankUser);

        bankUser.setPhoneNumberList(new HashSet<>(Set.of(phone)));
        bankUser.setEmailList(new HashSet<>(Set.of(email)));

        userRepository.save(bankUser);
        phoneRepository.save(phone);
        emailRepository.save(email);

        EntityManager manager = Persistence
                .createEntityManagerFactory("YourPersistenceUnit")
                .createEntityManager();

        //конструкция для предотвращения рекурсивного вызова объектов
        manager.getTransaction().begin();
        manager.persist(bankUser);
        manager.persist(phone);
        manager.persist(email);
        manager.getTransaction().commit();

        return bankUser;
    }


//        BankUser newU = userRepository.getReferenceById(id);
//        newU.setEmailList(Set.of(emailRepository.getReferenceById(eId)));
//        newU.setPhoneNumberList(Set.of(phoneRepository.getReferenceById(pId)));
//        Long newUId = userRepository.save(newU).getId();
//        Phone newP = phoneRepository.getReferenceById(pId);
//        newP.setBankUser(newU);
//        phoneRepository.save(newP);
//        Email newE = emailRepository.getReferenceById(eId);
//        newE.setBankUser(newU);
//        emailRepository.save(newE);
//
//        return userRepository.getReferenceById(newUId);

//        v.setEmailList(Set.of(e));
//        v.setPhoneNumberList(Set.of(p));


    public List<BankUser> readAll() {
        return userRepository.findAll();
    }

    //dto на апдейт email tel
    //достать текущий, замена из апдейта
    //вернуть в бд
    //везде дто(id, email, tel)
    public BankUser update(BankUserUpdateDTO dtoUpdate) {
//        BankUser bankUser = userRepository.getReferenceById(dtoUpdate.getId());


//        return userRepository.save(dtoUpdate);
        return null;
    }

    public void delete(BankUserDeleteDTO dtoDelete) {
        userRepository.deleteById(dtoDelete.getId());
    }

    //сама логика поиска и всех методов

    //дто на перевод (id1, id2, sum)
    //lock вручную
    //это переменна или массив, где челы, кому нельзя делать операции
}
