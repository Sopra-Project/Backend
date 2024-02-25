package com.sopra.parkingsystem.model.dto;

import com.sopra.parkingsystem.model.Building;
import lombok.Data;

@Data
public class BuildingDTO {
    public String name;
    public int totalparkingspots;

    public Building toBuilding() {
        return Building.builder()
                .name(name)
                .totalParkingSpots(totalparkingspots)
                .build();
    }
}
