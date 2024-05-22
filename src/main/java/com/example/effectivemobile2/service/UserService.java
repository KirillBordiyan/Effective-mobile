package com.example.effectivemobile2.service;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.*;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;

    //dto нужен только как набор параметров в коде
    // для создания экземпляра user, который и попадет в БД
    public BankUser create(BankUserCreateDTO dto) {
        BankUser v = new BankUser(dto.getLogin(), dto.getPassword(), dto.getInitialAmount(), dto.getBirthDate(), dto.getFullName());
        Long id = userRepository.save(v).getId();
        Email e = new Email(dto.getEmail());
        Phone p = new Phone(dto.getPhoneNumber());
        Long eId = emailRepository.save(e).getId();
        Long pId = phoneRepository.save(p).getId();

        BankUser newU = userRepository.getReferenceById(id);
        newU.setEmailList(Set.of(emailRepository.getReferenceById(eId)));
        newU.setPhoneNumberList(Set.of(phoneRepository.getReferenceById(pId)));
        Long newUId = userRepository.save(newU).getId();
        Phone newP = phoneRepository.getReferenceById(pId);
        newP.setBankUser(newU);
        phoneRepository.save(newP);
        Email newE = emailRepository.getReferenceById(eId);
        newE.setBankUser(newU);
        emailRepository.save(newE);

        return userRepository.getReferenceById(newUId);

//        v.setEmailList(Set.of(e));
//        v.setPhoneNumberList(Set.of(p));


//        return userRepository.getReferenceById(d.getId());
//        return userRepository.save(BankUser.builder()
//                .currentBalance(dto.getInitialAmount())
//                .phoneNumberList(Set.of(new Phone(dto.getPhoneNumber().getPhone())))
//                .emailList(Set.of(new Email(dto.getEmail().getEmail())))
//                        .phoneNumberList(Collections.singletonList(dto.getPhoneNumber().getPhone()))
//                .emailList(Collections.singletonList(dto.getPhoneNumber().getPhone()))
//                .birthDate(dto.getBirthDate())
//                .fullName(dto.getFullName())
//                .build());
    }

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
