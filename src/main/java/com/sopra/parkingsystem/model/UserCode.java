package com.sopra.parkingsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usercode")
public class UserCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "code")
    private String code;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @Column(name = "expiresat")
    private LocalDateTime expiresAt;

    public UserCode(User user) {
        this.user = user;
        this.code = generateCode();
        this.expiresAt = LocalDateTime.now(ZoneOffset.UTC).plusMinutes(5);
    }

    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now(ZoneOffset.UTC));
    }

    private String generateCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
