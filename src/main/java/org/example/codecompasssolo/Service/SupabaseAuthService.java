package org.example.codecompasssolo.Service;

import org.example.codecompasssolo.dto.LoginCredentials;
import org.example.codecompasssolo.entity.ProfileEntity;
import org.example.codecompasssolo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class SupabaseAuthService {

    @Autowired
    ProfileRepository profileRepository;

    @Value("${SUPABASE_PROJECT_URL}")
    String supabaseProjectUrl;

    @Value("${SUPABASE_ANON_API_KEY}")
    String supabase_anon_api_key;

    public ResponseEntity attemptLogin(String email, String password) {

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

                String accessToken = (String) responseBody.get("access_token");
                Map<String, Object> userMap = (Map<String, Object>) responseBody.get("user");

                UUID userId = UUID.fromString(userMap.get("id").toString());
                System.out.println("ID: " + userId);

                ProfileEntity userProfile = profileRepository.findById(userId);
                if (userProfile != null) {
                    if (userProfile.getRole().equals("ADMIN")) {
                        ResponseCookie cookie = ResponseCookie.from("jwt", accessToken)
                                .httpOnly(true)
                                .secure(true)
                                .sameSite("Strict")
                                .path("/")
                                .maxAge(3600)
                                .build();
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

                        return ResponseEntity.ok()
                                .headers(responseHeaders)
                                .body(Map.of("ok", true));
                    } else {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                    }
                }
            }
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //@TODO een processResponse method aanmaken, zodat het versturen van login attempt & afhandelen response, netjes
    //gesplit zijn.
}
