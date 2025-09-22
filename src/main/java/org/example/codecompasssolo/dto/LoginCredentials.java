package org.example.codecompasssolo.dto;

public class LoginCredentials {

    private String email;
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }

}
