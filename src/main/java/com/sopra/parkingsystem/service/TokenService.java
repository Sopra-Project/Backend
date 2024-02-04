package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Autowired
    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String encodeToken(User user) {
        Instant now = Instant.now();
        String role = user.getRole().getAuthority();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .subject(user.getName())
                .claim("role", role)
                .claim("totalparkingspots", user.getTotalparkingspots())
                .claim("totalparkingspotsavailable", user.getTotalparkingspotsavailable())
                .issuedAt(now)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
