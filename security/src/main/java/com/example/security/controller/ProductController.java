package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/public")
    public String publicapi(){
        return "Hello World";
    }

    @GetMapping("/admin")
    public String adminRestricted(){
        return "ADMIN RESTRICTED";
    }
    @GetMapping("/hr")
    public String hrRestricted(){
        return "HR Restricted";
    }
}
