package com.example.effectivemobile2.service;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.BankUser;
import com.example.effectivemobile2.entity.BankUserEntity;
import com.example.effectivemobile2.entity.Email;
import com.example.effectivemobile2.entity.Phone;
import com.example.effectivemobile2.repo.EmailRepository;
import com.example.effectivemobile2.repo.PhoneRepository;
import com.example.effectivemobile2.repo.UserRepository;
import lombok.AllArgsConstructor;
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

        BankUser bank_user = BankUser.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .currentBalance(dto.getInitialAmount())
                .birthDate(dto.getBirthDate())
                .fullName(dto.getFullName())
                .build();

        Phone phone = new Phone(dto.getPhoneNumber(), bank_user);
        Email email = new Email(dto.getEmail(), bank_user);

        if(bank_user.getPhoneList() == null){
            bank_user.setPhoneList(new HashSet<>());
            bank_user.getPhoneList().add(phone);
        }
        if(bank_user.getEmailList() == null){
            bank_user.setEmailList(new HashSet<>());
            bank_user.getEmailList().add(email);
        }

        userRepository.save(bank_user);
        phoneRepository.save(phone);
        emailRepository.save(email);

        return bank_user;
    }


    public List<BankUser> readAll() {
        return userRepository.findAll().stream().toList();
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
