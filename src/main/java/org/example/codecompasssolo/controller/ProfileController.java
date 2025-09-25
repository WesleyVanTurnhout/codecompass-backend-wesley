package org.example.codecompasssolo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.codecompasssolo.Service.SupabaseAuthService;
import org.example.codecompasssolo.entity.ProfileEntity;
import org.example.codecompasssolo.entity.UserEntity;
import org.example.codecompasssolo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;
import java.util.UUID;


@RestController
public class ProfileController {


    @Autowired
    private SupabaseAuthService supabaseAuthService;

    @Autowired
    ProfileRepository profileRepository;

    @GetMapping("/api/me")
    public ResponseEntity getProfile(HttpServletRequest request) {

        try {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {

                    if (cookie.getName().equals("jwt")) {
                        UserEntity userEntity = supabaseAuthService.getLoggedinUser(cookie.getValue());
                        UUID userId = userEntity.getId();
                        ProfileEntity profileEntity = profileRepository.findById(userId);
                        return ResponseEntity.ok(profileEntity);
                    }
                }
            } else {
                System.out.println("No access token");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}