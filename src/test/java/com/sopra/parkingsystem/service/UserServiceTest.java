package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.ParkingSystemApplication;
import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.model.Role;
import com.sopra.parkingsystem.model.User;
import com.sopra.parkingsystem.model.dto.EditUserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ParkingSystemApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    private final UserService userService;
    private final BuildingService buildingService;
    private final String BUILDING_NAME = "Test building";
    private Building building;
    private User user;
    private final String DUPLICATE_MAIL = "mail123@mail.com";

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
                .email(DUPLICATE_MAIL)
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
    @Order(1)
    void getAllUsersShouldBe3InBuilding() {
        assertEquals(3, userService.getUsersByBuildingId(building.getId()).size());
    }

    @Test
    @Order(2)
    void getUserByIdShouldBeSameUser() {
        User user = userService.getUserById(this.user.getId());
        assertEquals(this.user, user);
    }

    @Test
    @Order(3)
    void getUserByEmailShouldBeSameUser() {
        User user = userService.getUserByEmail(this.user.getEmail());
        assertEquals(this.user, user);
    }

    @Test
    @Order(4)
    void getUserByDifferentEmailShouldBeDifferentUser() {
        User user = userService.getUserByEmail("mail1234@mail.com");
        assertNotEquals(this.user, user);
    }

    @Test
    @Order(5)
    void createUserWithAlreadyExistingEmailShouldThrowException() {
        User user = User.builder()
                .name("Testmannen")
                .email(DUPLICATE_MAIL)
                .role(Role.USER)
                .building(building)
                .build();
        assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });
    }

    @Test
    @Order(6)
    void updateUserWithDifferentBuildingIdShouldReturn0() {
        EditUserDTO dto = EditUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roleId(user.getRole().getId())
                .build();
        assertEquals(0, userService.updateUser(dto, 999999999));
    }

    @Test
    @Order(7)
    void updateUserWithSameBuildingIdShouldReturn1() {
        EditUserDTO dto = EditUserDTO.builder()
                .id(user.getId())
                .name("new name")
                .email(user.getEmail())
                .roleId(user.getRole().getId())
                .build();
        assertEquals(1, userService.updateUser(dto, building.getId()));
    }

    @Test
    @Order(8)
    void updateUserWithNonExistingUserShouldReturn0() {
        EditUserDTO dto = EditUserDTO.builder()
                .id(999999999)
                .name("new name")
                .email("lol@lol.lol")
                .roleId(1)
                .build();
        assertEquals(0, userService.updateUser(dto, building.getId()));
    }


    @AfterAll
    public void tearDown() {
        userService.getUsersByBuildingId(building.getId())
                .forEach(userService::delete);
        buildingService.delete(building.getId());
    }

}