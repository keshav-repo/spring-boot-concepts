package com.example.openapiswagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to open api documentation";
    }

    @GetMapping("/home")
    public Message homePage(){
        Message homePageMessage = Message.builder()
                .message("This is home page")
                .statusCode(200)
                .path("/home")
                .build();
        return homePageMessage;
    }
}
