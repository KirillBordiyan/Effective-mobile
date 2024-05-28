package com.example.effectivemobile2.controller;

import com.example.effectivemobile2.dto.BankUserCreateDTO;
import com.example.effectivemobile2.dto.BankUserDeleteDTO;
import com.example.effectivemobile2.entity.bank_user.BankUser;
import com.example.effectivemobile2.entity.bank_user.BankUserEntity;
import com.example.effectivemobile2.entity.bank_user.BankUserError;
import com.example.effectivemobile2.entity.bank_user_list_error.BankUserList;
import com.example.effectivemobile2.repo.FilterParams;
import com.example.effectivemobile2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("manager/")
@AllArgsConstructor
@Tag(name = "Manager Controller", description = "Tutorial about manager API")
public class ManagerController {

    @Autowired
    private final UserService userService;

    @PostMapping("/create_user")
    @Operation(
            description = "Create a new user by input JSON data",
            tags = {"post", "admin action"}
    )
    public ResponseEntity<BankUserEntity> create(@RequestBody BankUserCreateDTO dto) {
        if (dto.getInitialAmount() < 0)
            return new ResponseEntity<>(new BankUserError("Error"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.create(dto), HttpStatus.OK);
    }

    @GetMapping("/get_all")
    @Operation(
            description = "Get Page of users from DB",
            tags = {"get", "admin action"}
    )
    public ResponseEntity<BankUserList> readAll(@RequestParam(defaultValue = "0") int page) {

        //пагинация
        //возвращаем здесь И в остальном целые страницы юзеров
        Page<BankUser> users = userService.readAll(page);
        Integer nextPageIs = (users.getTotalPages() > page + 1) ? page + 1 : null;
        return new ResponseEntity<>(new BankUserList(users.toList(), nextPageIs), HttpStatus.OK);
    }

    @GetMapping("/filter")
    @Operation(
            description = "Get Page of users from DB by parameter",
            tags = {"get", "admin action"}
    )
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

    @DeleteMapping("/delete_user")
    @Operation(
            description = "Delete user completely",
            tags = {"delete", "admin action"}
    )
    public HttpStatus deleteUser(@RequestBody BankUserDeleteDTO deleteDTO) {
        try {
            userService.deleteUser(deleteDTO);
            return HttpStatus.OK;
        } catch (InvalidDataAccessApiUsageException e) {
//            log.info(e.getMessage());
            return HttpStatus.BAD_REQUEST;
        }
    }
}
