package com.example.effectivemobile2.controller;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.dto.BankUserUpdateDTO;
import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.example.effectivemobile2.entity.bank_user.BankUserEntity;
import com.example.effectivemobile2.entity.bank_user.BankUserError;
import com.example.effectivemobile2.entity.bank_user_list_error.BankUserList;
import com.example.effectivemobile2.entity.user_params.UserParam;
import com.example.effectivemobile2.entity.user_params.UserParamError;
import com.example.effectivemobile2.exceptions.LastElementException;
import com.example.effectivemobile2.repo.FilterParams;
import com.example.effectivemobile2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("user/")
@AllArgsConstructor
@Tag(name = "User Controller", description = "Tutorial about user API")
public class UserController {

    @Autowired
    private final UserService userService;

    @DeleteMapping("/delete_param")
    @Operation(
            description = "Delete some phone/email in user card",
            tags = {"delete", "user action"}
    )
    public ResponseEntity<UserParam> deleteByParam(@RequestBody BankUserDeleteDTO deleteDTO) {
        try {
            return new ResponseEntity<>(userService.deleteByParam(deleteDTO), HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException | LastElementException | InvalidParameterException e) {
            return new ResponseEntity<>(new UserParamError("INCORRECT INPUT: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_param")
    @Operation(
            description = "Update user param by id and params necessary",
            tags = {"put", "user action"}
    )
    public ResponseEntity<BankUserEntity> update(@RequestBody BankUserUpdateDTO dto) {
        try {
            BankUserEntity bankUser = userService.update(dto);
//            log.error("TAG", "update: " + bankUser); //лог, что создан (после создания)
            return new ResponseEntity<>(bankUser, HttpStatus.OK);
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
}
