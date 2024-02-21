package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.ParkingSystemApplication;
import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ParkingSystemApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private final UserService userService;
    private final BuildingService buildingService;
    private final String BUILDING_NAME = "Test building";
    private Building building;
    private User user;

    @Autowired
    public UserServiceTest(UserService userService, BuildingService buildingService) {
        this.userService = userService;
        this.buildingService = buildingService;
    }

    @BeforeAll
    public void setUp() {
        Building building = Building.builder()
                .name(BUILDING_NAME)
                .totalParkingSpots(5)
                .build();
        buildingService.save(building);
        this.building = buildingService.getByName(BUILDING_NAME);

        User user = User.builder()
                .name("Test")
                .email("mail123@mail.com")
                .building(this.building)
                .role(Role.USER)
                .build();
        userService.save(user);
        this.user = userService.getUserByEmail("mail123@mail.com");

        User user2 = User.builder()
                .name("Tester")
                .email("mail1234@mail.com")
                .building(this.building)
                .role(Role.USER)
                .build();
        userService.save(user2);

        User user3 = User.builder()
                .name("Test11")
                .email("mail12345@mail.com")
                .building(this.building)
                .role(Role.USER)
                .build();
        userService.save(user3);

    }

    @Test
    void getAllUsersShouldBe3InBuilding() {
        assertEquals(3, userService.getUsersByBuildingId(building.getId()).size());
    }

    @Test
    void getUserByIdShouldBeSameUser() {
        User user = userService.getUserById(this.user.getId());
        assertEquals(this.user, user);
    }

    @Test
    void getUserByEmailShouldBeSameUser() {
        User user = userService.getUserByEmail(this.user.getEmail());
        assertEquals(this.user, user);
    }

    @Test
    void getUserByDifferentEmailShouldBeDifferentUser() {
        User user = userService.getUserByEmail("mail1234@mail.com");
        assertNotEquals(this.user, user);
    }

    @AfterAll
    public void tearDown() {
        userService.getUsersByBuildingId(building.getId())
                .forEach(userService::delete);
        buildingService.delete(building.getId());
    }

}