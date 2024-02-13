package com.sopra.parkingsystem.controller;

import com.sopra.parkingsystem.model.ParkingSpot;
import com.sopra.parkingsystem.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @RequestMapping("/all")
    public List<ParkingSpot> getAll() {
        return parkingService.getAllParkingSpots();
    }

    @PutMapping("/{id}/free")
    public void freeSpot(@PathVariable int id) {
        parkingService.unpark(id);
    }

    @GetMapping("/{id}")
    public ParkingSpot getById(@PathVariable long id) {
        return parkingService.getById(id);
    }

}
