package com.jlog.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthAndCustomAuthDemoController {

    @GetMapping("/me")
    public String index(@AuthenticationPrincipal User user) {
        return user.getUsername();
    }

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }
}
