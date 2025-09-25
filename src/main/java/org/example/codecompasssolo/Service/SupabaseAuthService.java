package org.example.codecompasssolo.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.codecompasssolo.dto.LoginCredentials;
import org.example.codecompasssolo.entity.ProfileEntity;
import org.example.codecompasssolo.entity.UserEntity;
import org.example.codecompasssolo.repository.ProfileRepository;
import org.example.codecompasssolo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.WebUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SupabaseAuthService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

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

    public ResponseEntity logOut(HttpServletRequest request){
        Cookie jwtCookie = WebUtils.getCookie(request, "jwt");

        if (jwtCookie != null) {
            String accessToken = jwtCookie.getValue();
            try {
                String logoutUrl = supabaseProjectUrl + "/auth/v1/logout";
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(accessToken);
                headers.set("apikey", supabase_anon_api_key);
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> logoutRequest = new HttpEntity<>(null, headers);

                ResponseEntity<String> response = restTemplate.exchange(
                        logoutUrl,
                        HttpMethod.POST,
                        logoutRequest,
                        String.class
                );

            } catch (Exception e) {
                System.out.println("Exception uitloggen: " + e.getMessage());
            }
        }

        ResponseCookie deleteCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.ok().headers(responseHeaders).build();
    }


    public UserEntity getLoggedinUser(String token) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabase_anon_api_key);
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<UserEntity> response = restTemplate.exchange(
                supabaseProjectUrl + "/auth/v1/user",
                HttpMethod.GET,
                request,
                UserEntity.class
        );

        return response.getBody();
    }
}
