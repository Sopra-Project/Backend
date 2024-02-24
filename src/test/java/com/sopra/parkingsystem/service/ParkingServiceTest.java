package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.ParkingSystemApplication;
import com.sopra.parkingsystem.model.*;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ParkingSystemApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParkingServiceTest {

    private final ParkingService parkingService;
    private final UserService userService;
    private final BuildingService buildingService;
    private final String EMAIL = "test123@test.test";
    private final String BUILDING_NAME = "Test building";
    private User user;
    private Building building;

    @Autowired
    public ParkingServiceTest(ParkingService parkingService, UserService userService, BuildingService buildingService) {
        this.userService = userService;
        this.parkingService = parkingService;
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

        user = User.builder()
                .name("Test")
                .email(EMAIL)
                .building(this.building)
                .role(Role.USER)
                .build();
        userService.save(user);

        user = userService.getUserByEmail(EMAIL);

        ParkingSpot parkingSpot = ParkingSpot.builder()
                .registrationNumber("TTT123")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot);

        ParkingSpot parkingSpot2 = ParkingSpot.builder()
                .registrationNumber("TTT124")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot2);

        ParkingSpot parkingSpot3 = ParkingSpot.builder()
                .registrationNumber("TTT125")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot3);

        ParkingSpot parkingSpot4 = ParkingSpot.builder()
                .registrationNumber("TTT126")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot4);

        ParkingSpot parkingSpot5 = ParkingSpot.builder()
                .registrationNumber("TTT127")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot5);
    }

    @Test
    @Order(1)
    void getAllParkingSpotsShouldBe5() {
        List<ParkingSpot> parkingSpots = parkingService.getParkingSpotsByBuildingId(user.getBuilding().getId());
        assertEquals(5, parkingSpots.size());
        assertNotEquals(0, parkingSpots.size());
    }

    @Test
    @Order(2)
    void tryingToAddTooManyParkingShouldFail() {
        ParkingSpot parkingSpot = ParkingSpot.builder()
                .registrationNumber("TTT128")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusMinutes(30))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        assertThrows(Exception.class, () -> parkingService.save(parkingSpot));
        List<ParkingSpot> parkingSpots = parkingService.getParkingSpotsByBuildingId(user.getBuilding().getId());
        assertEquals(5, parkingSpots.size());
    }

    @Test
    @Order(3)
    void parkingCarAfterOtherCarLeftShouldWork() {
        ParkingSpot parkingSpot = ParkingSpot.builder()
                .registrationNumber("TTT128")
                .startTime(LocalDateTime.now().plusHours(2))
                .endTime(LocalDateTime.now().plusHours(2).plusMinutes(30))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        assertDoesNotThrow(() -> parkingService.save(parkingSpot));
        List<ParkingSpot> parkingSpots = parkingService.getParkingSpotsByBuildingId(user.getBuilding().getId());
        assertEquals(6, parkingSpots.size());
    }

    @Test
    @Order(4)
    void findParkingSpotsFromToFromBuildingShouldBe6() {
        List<ParkingSpot> parkingSpots = parkingService.getParkingSpotsFromToFromBuilding(LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusHours(3), user.getBuilding().getId());
        assertEquals(6, parkingSpots.size());
    }

    @Test
    @Order(5)
    void findParkingSpotsBetween1And2ShouldBe5() {
        List<ParkingSpot> parkingSpots = parkingService.getParkingSpotsFromToFromBuilding(LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusHours(1), user.getBuilding().getId());
        assertEquals(5, parkingSpots.size());
    }

    @Test
    @Order(6)
    @Ignore
    void getAllMonthsParkingSpotsFromToFromBuildingShouldHave2Entries() {
        ParkingSpot parkingSpot6 = ParkingSpot.builder()
                .registrationNumber("TTT128")
                .startTime(LocalDateTime.now().plusDays(2))
                .endTime(LocalDateTime.now().plusHours(1))
                .status(new Status(1, "PARKED"))
                .user(user)
                .build();
        parkingService.save(parkingSpot6);

        Map<Integer, List<ParkingSpot>> parkingSpots = parkingService.getAllMonthsParkingSpotsFromToFromBuilding(user.getBuilding().getId());
        assertEquals(2, parkingSpots.size());
    }

    @Test
    @Order(7)
    void todayDateShouldBeAKeyInMap() {
        Map<Integer, List<ParkingSpot>> parkingSpots = parkingService.getAllMonthsParkingSpotsFromToFromBuilding(user.getBuilding().getId());
        assertTrue(parkingSpots.containsKey(LocalDateTime.now().getDayOfMonth()));
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