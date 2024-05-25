package com.example.effectivemobile2.controller;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.*;
import com.example.effectivemobile2.repo.FilterParams;
import com.example.effectivemobile2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    //создание нового пользователя
    //апи служебный
    @PostMapping("/create_user_db")
    public ResponseEntity<BankUserEntity> create(@RequestBody BankUserCreateDTO dto,
                                                 @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null)
            return new ResponseEntity<>(new BankUserError("Need Authorization"), HttpStatus.UNAUTHORIZED);
        else if (token.startsWith("Bearer")) {
            if (token.startsWith("0000000000", 7)) {
                if (dto.getInitialAmount() < 0)
                    return new ResponseEntity<>(new BankUserError("Error"), HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(userService.create(dto), HttpStatus.OK);
            } else return new ResponseEntity<>(new BankUserError("Incorrect Bearer token"), HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity<>(new BankUserError("Need Bearer token"), HttpStatus.UNAUTHORIZED);
    }

    //получение списка пользователей
    //служебный
    @GetMapping("/get_all")
    public ResponseEntity<BankUserList> readAll(@RequestParam(defaultValue = "0") int page) {
//        try{
        //пагинация
        //возвращаем здесь И в остальном целые страницы юзеров
        Page<BankUser> users = userService.readAll(page);
        Integer nextPage = (users.getTotalPages() > page + 1) ? page + 1 : null;
        return new ResponseEntity<>(new BankUserList(users.toList(), nextPage), HttpStatus.OK);

//        }catch (){
//
//        }
    }

    //служебный
    @GetMapping("/filter")
    public ResponseEntity<BankUserEntity> filterByParam(@RequestParam FilterParams filterBy,
                                                        @RequestParam String param,
                                                        @RequestParam(defaultValue = "0") int page) {
        try {
            Page<BankUser> users = userService.filter(filterBy, param, page);
            Integer nextPage = (users.getTotalPages() > page + 1) ? page + 1 : null;
            return new ResponseEntity<>(new BankUserList(users.toList(), nextPage), HttpStatus.OK);
        } catch (DateTimeParseException e) {
//            log.error(e.getMessage());
            return new ResponseEntity<>(new BankUserError("INCORRECT DATE FORMAT: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }
//    @GetMapping("/get/id") для получения другого типа инфы


    //вопросик, может быть и тот и тот, надо разделить
    //скорее всего пользовательский
    @PutMapping("/update_user")
    public ResponseEntity<BankUserEntity> update(@RequestBody BankUserUpdateDTO dto) {
        try {
            BankUserEntity bue = userService.update(dto);
//            log.error("TAG", "update: " + bue); //лог, что создан (после создания)
            return new ResponseEntity<>(bue, HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
//            log.info(e.getMessage());
            return new ResponseEntity<>(new BankUserError("INCORRECT ID: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
//            log.info(e.getMessage());
            return new ResponseEntity<>(new BankUserError("THE SAME VALUE ALREADY EXISTS: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (InvalidDataAccessApiUsageException e) {
//            log.info(e.getMessage());
            return new ResponseEntity<>(new BankUserError("ID IS NULL: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    //пользовательский для удаления телефона и почты


    //удаление пользователя из репозитория по id
    //служебный
    //доделать исключение
    @DeleteMapping("/delete_user_db")
    public HttpStatus delete(@RequestBody BankUserDeleteDTO dtoDelete) {
        try {
            userService.delete(dtoDelete);
            return HttpStatus.OK;
        } catch (InvalidDataAccessApiUsageException e) {
//            log.info(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }
}
