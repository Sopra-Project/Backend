package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final BuildingService buildingService;

    @Autowired
    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, BuildingService buildingService) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.buildingService = buildingService;
    }

    public String encodeToken(User user) {
        Instant now = Instant.now();
        String role = user.getRole().getAuthority();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .subject(user.getName())
                .claim("id", String.valueOf(user.getId()))
                .claim("role", role)
                .claim("name", user.getName())
                .claim("building", user.getBuilding().getName())
                .claim("buildingId", String.valueOf(user.getBuilding().getId()))
                .expiresAt(now.plusSeconds(60 * 60 * 24))
                .issuedAt(now)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateTokenForBuilding(int buildingId, String token) {
        Instant now = Instant.now();
        Building building = buildingService.getById(buildingId);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .subject(getName(token))
                .claim("id", String.valueOf(getUserId(token)))
                .claim("role", getRole(token))
                .claim("name", getName(token))
                .claim("building", building.getName())
                .claim("buildingId", String.valueOf(building.getId()))
                .expiresAt(now.plusSeconds(60 * 60 * 24))
                .issuedAt(now)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public Jwt decodeToken(String token) {
        return jwtDecoder.decode(token);
    }

    private Map<String, Object> getClaims(String token) {
        return jwtDecoder.decode(token).getClaims();
    }

    private String getClaim(String token, String claim) {
        return (String) getClaims(token).get(claim);
    }

    public String getRole(String token) {
        return getClaim(token, "role");
    }

    public String getName(String token) {
        return getClaim(token, "name");
    }

    public String getBuildingName(String token) {
        return getClaim(token, "building");
    }

    public int getBuildingId(String token) {
        return Integer.parseInt(getClaim(token, "buildingId"));
    }

    public int getUserId(String token) {
        return Integer.parseInt(getClaim(token, "id"));
    }

    public boolean isTokenValid(String token) {
        return jwtDecoder.decode(token).getExpiresAt().isAfter(Instant.now());
    }

}
