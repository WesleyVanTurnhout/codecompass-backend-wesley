package org.example.codecompasssolo.Service;

import org.example.codecompasssolo.dto.LoginCredentials;
import org.example.codecompasssolo.entity.ProfileEntity;
import org.example.codecompasssolo.entity.UserEntity;
import org.example.codecompasssolo.repository.ProfileRepository;
import org.example.codecompasssolo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupabaseAuthService {

    @Autowired
    ProfileRepository profileRepository;

    @Value("${SUPABASE_PROJECT_URL}")
    String supabaseProjectUrl;

    @Value("${SUPABASE_ANON_API_KEY}")
    String supabase_anon_api_key;

    public Boolean attemptLogin(String email, String password) {

        RestTemplate restTemplate = new RestTemplate();

        String loginUrl = supabaseProjectUrl + "/auth/v1/token?grant_type=password";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabase_anon_api_key);


        LoginCredentials loginCredentials = new LoginCredentials(email, password);
        HttpEntity<LoginCredentials> request = new HttpEntity<>(loginCredentials, headers);


        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();

                Map<String, Object> userMap = (Map<String, Object>) responseBody.get("user");

                UUID userId = UUID.fromString(userMap.get("id").toString());
                System.out.println("ID: " + userId);

                ProfileEntity userProfile = profileRepository.findById(userId);
                if (userProfile == null) {
                    return false;
                }
                if (userProfile.getRole().equals("ADMIN")) {
                    return true;
                }
            }
        } catch (Exception e) {
            //mogelijk invalid login credentials
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return false;
    }

    //@TODO een processResponse method aanmaken, zodat het versturen van login attempt & afhandelen response, netjes
    //gesplit zijn.
}
