package com.example.effectivemobile2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "Common Controller", description = "CommonController to example")
public class CommonController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
