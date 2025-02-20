package com.bakirwebservice.securityservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "api_keys")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ApiKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "api_key")
    private String apiKey;

    private boolean isActive = true;

    private boolean rotationRequired = false;

    @ElementCollection
    private List<String> permissions;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expiresAt);
    }

}