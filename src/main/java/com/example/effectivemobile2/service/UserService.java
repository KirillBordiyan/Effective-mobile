package com.example.effectivemobile2.service;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.example.effectivemobile2.entity.user_params.Email;
import com.example.effectivemobile2.entity.user_params.Phone;
import com.example.effectivemobile2.entity.user_params.UserParam;
import com.example.effectivemobile2.repo.EmailRepository;
import com.example.effectivemobile2.repo.FilterParams;
import com.example.effectivemobile2.repo.PhoneRepository;
import com.example.effectivemobile2.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;


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
                .birthDate(LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .fullName(dto.getFullName())
                .build();

        Phone phone = new Phone(dto.getPhoneNumber(), bank_user);
        Email email = new Email(dto.getEmail(), bank_user);

        if (bank_user.getPhones() == null) {
            bank_user.setPhones(new HashSet<>());
            bank_user.getPhones().add(phone);
        }
        if (bank_user.getEmails() == null) {
            bank_user.setEmails(new HashSet<>());
            bank_user.getEmails().add(email);
        }

        userRepository.save(bank_user);
        phoneRepository.save(phone);
        emailRepository.save(email);

        return bank_user;
    }

    public Page<BankUser> readAll(int page) throws NumberFormatException {
        Pageable pageable = PageRequest.of(page, 10);
        return userRepository.findAll(pageable);
    }


    public BankUser update(BankUserUpdateDTO dtoUpdate) throws
            IndexOutOfBoundsException,
            DataIntegrityViolationException,
            InvalidDataAccessApiUsageException {

        BankUser bankUser = userRepository.findById(dtoUpdate.getId()).stream().toList().get(0);

        if (dtoUpdate.getPhoneUpdate() != null) {
            bankUser.getPhones().add(new Phone(dtoUpdate.getPhoneUpdate(), bankUser));
        }
        if (dtoUpdate.getEmailUpdate() != null) {
            bankUser.getEmails().add(new Email(dtoUpdate.getEmailUpdate(), bankUser));
        }

        userRepository.save(bankUser);
        return bankUser;
    }

//    public BankUser deleteNumber() {
//        BankUser bankUser = userRepository.findAllById().stream().toList().get(0);
//
//    }

//    public BankUser deleteEmail() {
//        BankUser bankUser = userRepository.findAllById().stream().toList().get(0);
//
//    }


    public void deleteUser(BankUserDeleteDTO deleteDTO) throws InvalidDataAccessApiUsageException {
        userRepository.deleteById(deleteDTO.getId());
    }


    public UserParam deleteByParam(BankUserDeleteDTO deleteDTO) throws InvalidDataAccessApiUsageException{
        if(deleteDTO.getPhoneDelete() != null){
            return deleteByPhone(deleteDTO.getPhoneDelete());
        }
        if(deleteDTO.getEmailDelete() != null){
            return deleteByEmail(deleteDTO.getEmailDelete());
        }
        return null;
    }

    public Phone deleteByPhone(String phone) throws InvalidDataAccessApiUsageException {
        return phoneRepository.deleteByPhone(phone);
    }

    public Email deleteByEmail(String email) throws InvalidDataAccessApiUsageException {
        return emailRepository.deleteByEmail(email);
    }

    public Page<BankUser> filter(FilterParams filterBy, String param, int page) throws DateTimeParseException {
        //для пагинации
        Pageable pageable = PageRequest.of(page, 10);

        return switch (filterBy) {
            case FULL_NAME: {
                yield userRepository.findByFullNameContaining(param, pageable);
            }
            case PHONE: {
                Phone phone = phoneRepository.findByPhone(param);
                yield userRepository.findByPhones(phone, pageable);
            }
            case EMAIL: {
                Email email = emailRepository.findByEmail(param);
                yield userRepository.findByEmails(email, pageable);
            }
            case BIRTH_DATE: {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(param, formatter);
                System.out.println(param);
                System.out.println(date);
                yield userRepository.findByBirthDateGreaterThanEqual(date, pageable);
            }
        };
    }
}
