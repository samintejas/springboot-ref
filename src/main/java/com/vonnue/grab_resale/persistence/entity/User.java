package com.vonnue.grab_resale.persistence.entity;

import java.time.Instant;

import com.vonnue.grab_resale.common.constants.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    private String password;

    @Setter
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Setter
    private String inviteToken;

    @Setter
    private Instant inviteTokenExpiry;

    public User(String email, String password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public User(String email, String name, Role role, String inviteToken, Instant inviteTokenExpiry) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.inviteToken = inviteToken;
        this.inviteTokenExpiry = inviteTokenExpiry;
    }

    public boolean isInviteTokenValid() {
        return inviteToken != null && inviteTokenExpiry != null && Instant.now().isBefore(inviteTokenExpiry);
    }
}
