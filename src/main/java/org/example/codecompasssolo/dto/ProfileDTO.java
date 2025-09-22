package org.example.codecompasssolo.dto;

import java.util.UUID;

public class ProfileDTO {
    private UUID id;
    private String displayName;
    private String role;

    public UUID getId() { return id; }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
