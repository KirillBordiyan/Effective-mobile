package com.example.effectivemobile2.repo;

import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.example.effectivemobile2.entity.user_params.Email;
import com.example.effectivemobile2.entity.user_params.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Component
public class DataBaseInitializer implements CommandLineRunner {


    private final UserRepository userRepository;

    private final PhoneRepository phoneRepository;

    private final EmailRepository emailRepository;
    @Autowired
    private PasswordEncoder ps;

    @Autowired
    public DataBaseInitializer(UserRepository userRepository,
                               PhoneRepository phoneRepository,
                               EmailRepository emailRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.count() != 0 ){
            return;
        }

        BankUser admin = BankUser.builder()
                .login("admin_1")
                .password(ps.encode("admin_1"))
                .currentBalance(1)
                .birthDate(LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .fullName("admin 1 name")
                .role("ADMIN")
                .build();

        Phone phone = new Phone("+1", admin);
        Email email = new Email("admin1@mail.com", admin);

        admin.setPhones(new HashSet<>());
        admin.getPhones().add(phone);


        admin.setEmails(new HashSet<>());
        admin.getEmails().add(email);


        userRepository.save(admin);
        phoneRepository.save(phone);
        emailRepository.save(email);
    }
}
