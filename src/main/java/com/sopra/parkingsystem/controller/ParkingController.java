package com.sopra.parkingsystem.controller;

import com.sopra.parkingsystem.model.ParkingSpot;
import com.sopra.parkingsystem.service.ParkingService;
import com.sopra.parkingsystem.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final TokenService tokenService;

    @Autowired
    public ParkingController(ParkingService parkingService, TokenService tokenService) {
        this.tokenService = tokenService;
        this.parkingService = parkingService;
    }

    @RequestMapping("/all")
    public List<ParkingSpot> getAll(final JwtAuthenticationToken token) {
        int buildingId = tokenService.getBuildingId(token.getToken().getTokenValue());
        return parkingService.getParkingSpotsByBuildingId(buildingId);
    }

    @PutMapping("/{id}/free")
    public void freeSpot(@PathVariable int id) {
        parkingService.unpark(id);
    }

    @GetMapping("/{id}")
    public ParkingSpot getById(@PathVariable int id) {
        return parkingService.getById(id);
    }

    @GetMapping("/date")
    public List<ParkingSpot> getByDate(
            final JwtAuthenticationToken token,
            @RequestParam("startdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam("enddate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        int buildingId = tokenService.getBuildingId(token.getToken().getTokenValue());
        return parkingService.getParkingSpotsFromToFromBuilding(date, endDate, buildingId);
    }

    @GetMapping("/month")
    public Map<Integer, List<ParkingSpot>> getByMonth(final JwtAuthenticationToken token) {
        int buildingId = tokenService.getBuildingId(token.getToken().getTokenValue());
        return parkingService.getAllMonthsParkingSpotsFromToFromBuilding(buildingId);
    }

}
