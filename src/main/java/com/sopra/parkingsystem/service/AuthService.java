package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthService(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public String login(String email) {
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return tokenService.encodeToken(user);
        }
        return null;
    }
}
