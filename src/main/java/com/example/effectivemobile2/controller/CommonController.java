package com.example.effectivemobile2.controller;

import com.example.effectivemobile2.entity.LoginForm;
import com.example.effectivemobile2.security.BankUserDetailService;
import com.example.effectivemobile2.webtoken.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Common Controller", description = "CommonController to example")
public class CommonController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BankUserDetailService bankUserDetailService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.login(), loginForm.password())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(bankUserDetailService.loadUserByUsername(loginForm.login()));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }

    }
}
