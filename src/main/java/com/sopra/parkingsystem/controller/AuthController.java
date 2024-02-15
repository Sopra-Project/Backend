package com.sopra.parkingsystem.controller;

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
        String token = authService.login(authDTO.getEmail()); //todo connect to mail service
        if (token == null) {
            return ResponseEntity.badRequest().body("Invalid email");
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/code")
    public ResponseEntity<String> code(@RequestBody CodeAuthDTO dto) {
        if (authService.validateCode(dto)) {
            return ResponseEntity.ok("Code is valid");
        }
        return ResponseEntity.badRequest().body("Code is invalid");
    }

}
