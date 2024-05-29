package com.example.effectivemobile2.controller;

import com.example.effectivemobile2.dto.jwt.JwtRequest;
import com.example.effectivemobile2.dto.jwt.JwtResponse;
import com.example.effectivemobile2.exceptions.AppError;
import com.example.effectivemobile2.service.UserService;
import com.example.effectivemobile2.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/authorization")
public class AuthController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    //для просмотра токена
    @PostMapping("/token")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest authRequest) {
        try {//проверка существования
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getLogin(),
                            authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new AppError(HttpStatus.UNAUTHORIZED.value(), "INCORRECT LOGIN OR PASSWORD"),
                    HttpStatus.UNAUTHORIZED);
        }

        //достать пользователя и создать по его данным токен
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }
}
