package com.example.effectivemobile2.service;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.*;
import jakarta.persistence.EntityManager;
//import javax.persistence.*;
import jakarta.persistence.Persistence;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//import static com.example.effectivemobile2.entity.UserRepository.manager;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;


    //dto нужен только как набор параметров в коде
    // для создания экземпляра user, который и попадет в БД
    public BankUser create(BankUserCreateDTO dto) {

        EntityManager manager = Persistence
                .createEntityManagerFactory("MyU")
                .createEntityManager();

        manager.getTransaction().begin();

        BankUser bank_user = BankUser.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .currentBalance(dto.getInitialAmount())
                .birthDate(dto.getBirthDate())
                .fullName(dto.getFullName())
                .build();


        Phone phone = new Phone(dto.getPhoneNumber(), bank_user);
        Email email = new Email(dto.getEmail(), bank_user);

        bank_user.setPhoneNumberList(new HashSet<>(Set.of(phone)));
        bank_user.setEmailList(new HashSet<>(Set.of(email)));


        userRepository.save(bank_user);
        phoneRepository.save(phone);
        emailRepository.save(email);

        System.out.println("\n"+bank_user+"\n");
        //конструкция для предотвращения рекурсивного вызова объектов


//        manager.persist(bank_user);
//        manager.persist(phone);
//        manager.persist(email);

        manager.getTransaction().commit();

        return bank_user;
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


//    public List<BankUser> readAll() {
////        return userRepository.findAll()
////                .stream()
////                .toList();
////        return manager.createQuery("from "+ BankUser.class.getName()).getResultList();
//        EntityManager manager = userRepository.getUserEntityManager();
//        return manager.createQuery("from BankUser").getResultList();
//    }

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
