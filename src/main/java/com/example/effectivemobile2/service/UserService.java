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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;

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

        if(bank_user.getPhones() == null){
            bank_user.setPhones(new HashSet<>());
            bank_user.getPhones().add(phone);
        }
        if(bank_user.getEmails() == null){
            bank_user.setEmails(new HashSet<>());
            bank_user.getEmails().add(email);
        }

        userRepository.save(bank_user);
        phoneRepository.save(phone);
        emailRepository.save(email);

        return bank_user;
    }

    public List<BankUser> readAll() {
        return userRepository.findAll().stream().toList();
    }

    public BankUser update(BankUserUpdateDTO dtoUpdate) {
        //IndexBound - елси id нет/неправильный
        // DataIntegrityViolationException - если такой параметр уже существует
        //InvalidDataAccessApiUsageException - если в id передали null

        BankUser bankUser = userRepository.findById(dtoUpdate.getId()).stream().toList().get(0);
        System.out.println(bankUser);

        if(dtoUpdate.getPhoneUpdate() != null ){
            bankUser.getPhones().add(new Phone(dtoUpdate.getPhoneUpdate(), bankUser));
        }
        if(dtoUpdate.getEmailUpdate() != null){
            bankUser.getEmails().add(new Email(dtoUpdate.getEmailUpdate(), bankUser));
        }

        userRepository.save(bankUser);
        return bankUser;
    }

    public void delete(BankUserDeleteDTO dtoDelete) {
        userRepository.deleteById(dtoDelete.getId());
    }


}
