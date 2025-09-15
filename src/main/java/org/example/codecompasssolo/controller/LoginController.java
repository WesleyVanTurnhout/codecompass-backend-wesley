package org.example.codecompasssolo.controller;

import org.example.codecompasssolo.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/api/login")
    public String attemptLogin(@RequestParam String email, @RequestParam String password) {
        System.out.print(email);
        return loginService.attemptLogin(email, password);
    }


}
