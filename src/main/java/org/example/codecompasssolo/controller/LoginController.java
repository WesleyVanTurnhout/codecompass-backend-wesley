package org.example.codecompasssolo.controller;

import org.example.codecompasssolo.Service.SupabaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private SupabaseAuthService supabaseAuthService;

    @PostMapping("/api/login")
    public ResponseEntity attemptLogin(@RequestParam String email, @RequestParam String password) {
        return supabaseAuthService.attemptLogin(email, password);
    }


}
