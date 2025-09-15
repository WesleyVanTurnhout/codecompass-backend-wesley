package org.example.codecompasssolo.Service;


import org.example.codecompasssolo.entity.ProfileEntity;
import org.example.codecompasssolo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.parser.Entity;

@Service
public class LoginService {

    @Autowired
    private ProfileRepository profileRepository;

    public String attemptLogin( String username) {
        ProfileEntity s = profileRepository.findByDisplayName(username);
        if (s == null) {
            return "Username not found";
        }
        return "profile: " + s;
    }
}
