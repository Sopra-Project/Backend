package com.sopra.parkingsystem.controller;

import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.dto.BuildingDTO;
import com.sopra.parkingsystem.model.dto.CreateAdminUser;
import com.sopra.parkingsystem.service.BuildingService;
import com.sopra.parkingsystem.service.TokenService;
import com.sopra.parkingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {
    private final BuildingService buildingService;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public SuperAdminController(BuildingService buildingService, UserService userService, TokenService tokenService) {
        this.buildingService = buildingService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/building")
    public void createBuilding(final JwtAuthenticationToken token, @RequestBody BuildingDTO dto) {
        final String tokenString = token.getToken().getTokenValue();
        if (!tokenService.getRole(tokenString).equals(Role.SUPER_ADMIN.getAuthority())) {
            throw new RuntimeException("You are not authorized to perform this action");
        }
        buildingService.save(dto.toBuilding());
    }

    @GetMapping("/buildings")
    public List<Building> getBuildings() {
        return buildingService.getAll();
    }

    @PostMapping("/user")
    public void createUser(@RequestBody CreateAdminUser dto) {
        userService.save(dto.toUser());
    }


}
