package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.ParkingSystemApplication;
import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {ParkingSystemApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;
    private User user;

    @BeforeAll
    public void setUp() {
        user = User.builder()
                .name("Test User")
                .email("tester123@test.tester")
                .building(new Building(1, "Test Building", 5))
                .role(Role.USER)
                .build();
    }

    @Test
    void generateToken() {
        String token = tokenService.encodeToken(user);
        assertNotNull(token);
    }

    @Test
    void decodeToken() {
        String token = tokenService.encodeToken(user);
        assertNotNull(tokenService.decodeToken(token));
    }

    @Test
    void getRole() {
        String token = tokenService.encodeToken(user);
        assertEquals("USER", tokenService.getRole(token));
    }

    @Test
    void getName() {
        String token = tokenService.encodeToken(user);
        assertEquals("Test User", tokenService.getName(token));
    }

    @Test
    void getBuilding() {
        String token = tokenService.encodeToken(user);
        assertEquals("Test Building", tokenService.getBuildingName(token));
    }

    @Test
    void isTokenValid() {
        String token = tokenService.encodeToken(user);
        assertTrue(tokenService.isTokenValid(token));
    }
}