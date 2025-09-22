package org.example.codecompasssolo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.codecompasssolo.Service.SupabaseAuthService;
import org.example.codecompasssolo.dto.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private SupabaseAuthService supabaseAuthService;

    @PostMapping("/api/login")
    public ResponseEntity<?> attemptLogin(@RequestBody LoginCredentials credentials) {
        return supabaseAuthService.attemptLogin(credentials.getEmail(), credentials.getPassword());
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return supabaseAuthService.logOut(request);
    }
}
