package com.sopra.parkingsystem.controller;

import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.User;
import com.sopra.parkingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public String me(Principal principal) {
        return "Hello " + principal.getName() + "!";
    }

    @GetMapping("/all")
    public List<User> all() {
        return userService.getAllUsers();
    }

    @GetMapping("/user")
    public Optional<User> user() {
        return userService.getUserById(1);
    }

}
