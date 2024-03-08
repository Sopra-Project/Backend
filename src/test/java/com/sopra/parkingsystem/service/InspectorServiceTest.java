package com.sopra.parkingsystem.service;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.sopra.parkingsystem.ParkingSystemApplication;
import com.sopra.parkingsystem.model.*;
import com.sopra.parkingsystem.model.dto.CreateUserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ParkingSystemApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InspectorServiceTest {

    private final ParkingService parkingService;
    private final UserService userService;
    private final BuildingService buildingService;
    private final InspectorService inspectorService;
    private final String EMAIL = "test123@test.test";
    private final String BUILDING_NAME = "Test building";
    private User user;
    private Building building;

    @Autowired
    public InspectorServiceTest(ParkingService parkingService, UserService userService, BuildingService buildingService, InspectorService inspectorService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.buildingService = buildingService;
        this.inspectorService = inspectorService;
    }

    @BeforeAll
    public void setUp() {
        Building building = Building.builder()
                .name(BUILDING_NAME)
                .totalParkingSpots(5)
                .build();
        buildingService.save(building);
        this.building = buildingService.getByName(BUILDING_NAME);

        CreateUserDTO create = CreateUserDTO.builder()
                .name("Test")
                .email(EMAIL)
                .roleId(Role.USER.getId())
                .build();
        userService.createUser(create, this.building.getId());
        this.user = userService.getUserByEmail(EMAIL);

        ParkingSpot parkingSpot = ParkingSpot.builder()
                .registrationNumber("TTT123")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot);

        parkingSpot = ParkingSpot.builder()
                .registrationNumber("TTT124")
                .startTime(LocalDateTime.now().minusHours(2))
                .endTime(LocalDateTime.now().minusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot);

        parkingSpot = ParkingSpot.builder()
                .registrationNumber("YYY111")
                .startTime(LocalDateTime.now().plusHours(2))
                .endTime(LocalDateTime.now().plusHours(4))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot);
    }

    @Test
    void isCarRegisteredShouldBeTrue() {
        JsonObject jsonObject = inspectorService.isCarRegistered("TTT123");
        assertTrue(jsonObject.get("valid").getAsBoolean());
    }

    @Test
    void isCarRegisteredShouldBeFalse() {
        JsonObject jsonObject = inspectorService.isCarRegistered("TTT124");
        assertFalse(jsonObject.get("valid").getAsBoolean());
    }

    @Test
    void carThatIsntRegisteredOrInDB() {
        JsonObject jsonObject = inspectorService.isCarRegistered("TTT125");
        assertFalse(jsonObject.get("valid").getAsBoolean());
    }

    @Test
    void carThatIsRegisteredShouldHaveMinutesLeft() {
        JsonObject jsonObject = inspectorService.isCarRegistered("TTT123");
        assertTrue(jsonObject.get("minutesLeft").getAsInt() > 0);
    }

    @Test
    void carThatIsRegisteredButNotNowShouldFail() {
        JsonObject jsonObject = inspectorService.isCarRegistered("YYY111");
        assertFalse(jsonObject.get("valid").getAsBoolean());
    }

    @AfterAll
    public void tearDown() {
        User user = userService.getUserByEmail(EMAIL);
        parkingService.getParkingSpotsByBuildingId(user.getBuilding().getId())
                .forEach(parkingService::delete);
        userService.delete(user);
        Building building = buildingService.getByName(BUILDING_NAME);
        buildingService.delete(building.getId());
    }
}