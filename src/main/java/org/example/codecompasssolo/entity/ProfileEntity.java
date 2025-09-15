package org.example.codecompasssolo.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "profile", schema = "public")
public class ProfileEntity {

    @Id
    private UUID id;

    @Column(name = "display_name")
    private String displayName;

    private String role;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // One-to-One relationship to UsersEntity
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;

    // Getters and Setters ...
}
