package com.example.effectivemobile2.service;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.example.effectivemobile2.entity.user_params.Email;
import com.example.effectivemobile2.entity.user_params.Phone;
import com.example.effectivemobile2.entity.user_params.UserParam;
import com.example.effectivemobile2.exceptions.LastElementException;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    @Autowired
    private PasswordEncoder ps;


    public Optional<BankUser> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    public BankUser create(BankUserCreateDTO dto) {

        BankUser bank_user = BankUser.builder()
                .login(dto.getLogin())
                .password(ps.encode(dto.getPassword()))
                .currentBalance(dto.getInitialAmount())
                .birthDate(LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .fullName(dto.getFullName())
                .role(dto.getRoles())
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

    public void deleteUser(BankUserDeleteDTO deleteDTO) throws InvalidDataAccessApiUsageException {
        userRepository.deleteById(deleteDTO.getId());
    }

    public UserParam deleteByParam(BankUserDeleteDTO deleteDTO) throws InvalidDataAccessApiUsageException {
        BankUser user = userRepository.findById(deleteDTO.getId()).stream().toList().get(0);
        if (deleteDTO.getPhoneDelete() != null) {
            return deleteNumber(user, deleteDTO.getPhoneDelete());
        }
        if (deleteDTO.getEmailDelete() != null) {
            return deleteEmail(user, deleteDTO.getEmailDelete());
        }
        throw new InvalidParameterException("INVALID PARAMETER: PHONE OR EMAIL MUST NOT BE NULL");
    }

    public Phone deleteNumber(BankUser currentUser, String phone) {
        Phone curPhone = phoneRepository.findByNumber(phone);
        if (phoneRepository.count() > 1) {
            currentUser.getPhones().remove(curPhone);
            phoneRepository.deleteById(curPhone.getId());
            userRepository.save(currentUser);
        } else {
            throw new LastElementException("THE ELEMENT PHONE IS LAST");
        }
        return curPhone;
    }

    public Email deleteEmail(BankUser currentUser, String email) {
        Email curEmail = emailRepository.findByMail(email);
        if (emailRepository.count() > 1) {
            currentUser.getEmails().remove(curEmail);
            emailRepository.deleteById(curEmail.getId());
            userRepository.save(currentUser);
        } else {
            throw new LastElementException("THE ELEMENT EMAIL IS LAST");
        }
        return curEmail;
    }

    public Page<BankUser> filter(FilterParams filterBy, String param, int page) throws
            NullPointerException {
        //для пагинации
        Pageable pageable = PageRequest.of(page, 10);

        return switch (filterBy) {
            case FULL_NAME: {
                yield userRepository.findByFullNameContaining(param, pageable);
            }
            case PHONE: {
                Phone phone = phoneRepository.findByNumber("+" + param);
                yield userRepository.findByPhones(phone, pageable);
            }
            case EMAIL: {
                Email email = emailRepository.findByMail(param);
                yield userRepository.findByEmails(email, pageable);
            }
            case BIRTH_DATE: {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(param, formatter);
                yield userRepository.findByBirthDateGreaterThanEqual(date, pageable);
            }
        };
    }

    public BankUser getCurrentInfo(Principal principal) {
        return findByLogin(principal.getName()).get();
    }

}
