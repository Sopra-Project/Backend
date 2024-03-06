package com.sopra.parkingsystem.controller;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.sopra.parkingsystem.model.dto.AuthDTO;
import com.sopra.parkingsystem.model.dto.CodeAuthDTO;
import com.sopra.parkingsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        JsonObject response = authService.login(authDTO.getEmail());
        if (response == null) {
            return ResponseEntity.badRequest().body("Invalid email");
        }
        return ResponseEntity.ok(response.toString());
    }

    @PostMapping("/code")
    public ResponseEntity<String> code(@RequestBody CodeAuthDTO dto) {
        if (authService.validateCode(dto)) {
            JsonObject response = authService.generateToken(dto.getEmail());
            return ResponseEntity.ok(response.toString());
        }
        return ResponseEntity.badRequest().body("Code is invalid");
    }

    @GetMapping("/validate/token")
    public ResponseEntity<String> validateToken(final JwtAuthenticationToken token) {
        if (authService.validateToken(token.getToken().getTokenValue())) {
            return ResponseEntity.ok("Token is valid");
        }
        return ResponseEntity.badRequest().body("Token is invalid");
    }

}
