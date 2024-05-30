package com.example.effectivemobile2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "User Controller", description = "Tutorial about user API")
public class PageController {

    @GetMapping("/common/home_page")
    @Operation(
            description = "Show home page (permitAll)",
            tags = {"get page"}
    )
    public String getHomePageCommon(){
        return "home_page.html";
    }

    @GetMapping("/user/home_page")
    @Operation(
            description = "Show home page (admin + user roles)",
            tags = {"get page"}
    )
    public String getHomePageUser(){
        return "home_page_user.html";
    }

    @GetMapping("/admin/home_page")
    @Operation(
            description = "Show home page (admin only)",
            tags = {"get page"}
    )
    public String getHomePageAdmin(){
        return "home_page_admin.html";
    }
}
