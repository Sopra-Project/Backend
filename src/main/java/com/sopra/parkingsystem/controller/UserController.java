package com.sopra.parkingsystem.controller;

import com.sopra.parkingsystem.model.User;
import com.sopra.parkingsystem.service.TokenService;
import com.sopra.parkingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllByBuildingId(final JwtAuthenticationToken token) {
        int buildingId = tokenService.getBuildingId(token.getToken().getTokenValue());
        return userService.getUsersByBuildingId(buildingId);
    }

}
