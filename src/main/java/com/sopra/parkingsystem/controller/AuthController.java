package com.sopra.parkingsystem.controller;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.sopra.parkingsystem.model.dto.AuthDTO;
import com.sopra.parkingsystem.model.dto.CodeAuthDTO;
import com.sopra.parkingsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.ok(authService.generateToken(dto.getEmail()));
        }
        return ResponseEntity.badRequest().body("Code is invalid");
    }

}
