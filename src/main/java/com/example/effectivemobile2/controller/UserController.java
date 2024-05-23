package com.example.effectivemobile2.controller;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.*;
import com.example.effectivemobile2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    //далее методы обращения к сервису

    //создание нового пользователя
    @PostMapping("/create_user_db")
    public ResponseEntity<BankUserEntity> create(@RequestBody BankUserCreateDTO dto) {
        if (dto.getInitialAmount() < 0)
            return new ResponseEntity<>(new BankUserError("Error"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.create(dto), HttpStatus.OK);
    }

    //получение списка пользователей
//    @GetMapping("/get_all")
//    public ResponseEntity<List<BankUser>>  readAll() {
//        return new ResponseEntity<>(userService.readAll(), HttpStatus.OK);
//    }

//    @GetMapping("/get/id") для получения другого типа инфы


    //метод обновления данных в репозитории
    //через сохранение, не dto
    @PutMapping("/update_user")
    public ResponseEntity<BankUser> update(@RequestBody BankUserUpdateDTO bankUser) {
        return new ResponseEntity<>(userService.update(bankUser), HttpStatus.OK);
    }

    //удаление пользователя из репозитория по id
    @DeleteMapping("/delete_user_db")
    public HttpStatus delete(@RequestBody BankUserDeleteDTO dtoDelete) {
        userService.delete(dtoDelete);
        return HttpStatus.OK;
    }
}
