package com.example.effectivemobile2.security;


import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.example.effectivemobile2.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
       Optional<BankUser> user = userRepository.findByLogin(login);

       if(user.isPresent()){
            return User.builder()
                    .username(user.get().getLogin())
                    .password(user.get().getPassword())
                    .roles(getRole(user.get()))
                    .build();
       }else {
           throw new UsernameNotFoundException(login);
       }

    }

    private String[] getRole(BankUser bankUser) {
        if(bankUser.getRole() == null){
            return new String[]{"USER"};
        }
        return bankUser.getRole().split(",");
    }
}
