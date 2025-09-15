package org.example.codecompasssolo.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "auth")
public class UserEntity {

    @Id
    private UUID id;

    @Column(name = "instance_id")
    private UUID instanceId;

    private String aud;
    private String role;
    private String email;

    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @Column(name = "email_confirmed_at")
    private OffsetDateTime emailConfirmedAt;

    @Column(name = "invited_at")
    private OffsetDateTime invitedAt;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "confirmation_sent_at")
    private OffsetDateTime confirmationSentAt;

    @Column(name = "recovery_token")
    private String recoveryToken;

    @Column(name = "recovery_sent_at")
    private OffsetDateTime recoverySentAt;

    @Column(name = "email_change_token_new")
    private String emailChangeTokenNew;

    @Column(name = "email_change")
    private String emailChange;

    @Column(name = "email_change_sent_at")
    private OffsetDateTime emailChangeSentAt;

    @Column(name = "last_sign_in_at")
    private OffsetDateTime lastSignInAt;

    @Column(name = "raw_app_meta_data", columnDefinition = "jsonb")
    private String rawAppMetaData;

    @Column(name = "raw_user_meta_data", columnDefinition = "jsonb")
    private String rawUserMetaData;

    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private String phone;

    @Column(name = "phone_confirmed_at")
    private OffsetDateTime phoneConfirmedAt;

    @Column(name = "phone_change")
    private String phoneChange;

    @Column(name = "phone_change_token")
    private String phoneChangeToken;

    @Column(name = "phone_change_sent_at")
    private OffsetDateTime phoneChangeSentAt;

    // Note: This is a generated column in PostgreSQL
    @Column(name = "confirmed_at", insertable = false, updatable = false)
    private OffsetDateTime confirmedAt;

    @Column(name = "email_change_token_current")
    private String emailChangeTokenCurrent;

    @Column(name = "email_change_confirm_status")
    private Short emailChangeConfirmStatus;

    @Column(name = "banned_until")
    private OffsetDateTime bannedUntil;

    @Column(name = "reauthentication_token")
    private String reauthenticationToken;

    @Column(name = "reauthentication_sent_at")
    private OffsetDateTime reauthenticationSentAt;

    @Column(name = "is_sso_user", nullable = false)
    private boolean isSsoUser;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "is_anonymous", nullable = false)
    private boolean isAnonymous;

    // One-to-One relationship with ProfileEntity
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    // Getters and Setters ...
}
