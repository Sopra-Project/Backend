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
@RequestMapping("/api/admin/")
public class AdminController {
    private final BuildingService buildingService;
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AdminController(BuildingService buildingService, UserService userService, TokenService tokenService) {
        this.buildingService = buildingService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/building")
    public void createBuilding(@RequestBody BuildingDTO dto) {
        buildingService.save(dto.toBuilding());
    }

    @GetMapping("/buildings")
    public List<Building> getBuildings() {
        return buildingService.getAll();
    }

    @GetMapping("/impersonate/{buildingId}")
    public String impersonate(final JwtAuthenticationToken token, @PathVariable Integer buildingId) {
        final String tokenString = token.getToken().getTokenValue();
        return tokenService.generateTokenForBuilding(buildingId, tokenString);
    }


}
