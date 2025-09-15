package org.example.codecompasssolo.Service;


import org.example.codecompasssolo.entity.ProfileEntity;
import org.example.codecompasssolo.entity.UserEntity;
import org.example.codecompasssolo.repository.ProfileRepository;
import org.example.codecompasssolo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.parser.Entity;

@Service
public class LoginService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;

    public String attemptLogin(String email, String password) {
        UserEntity user = userRepository.findByEmailAndEncryptedPassword(email, password);
        if (user == null) {
            return "User and password combination not found";
        }
        return "User: " + user;
    }
}
