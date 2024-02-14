package com.sopra.parkingsystem.service;

import com.sopra.parkingsystem.model.Building;
import com.sopra.parkingsystem.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {
    private final BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public void save(Building building) {
        buildingRepository.save(building);
    }

    public void delete(int id) {
        buildingRepository.deleteById(id);
    }

    public Building getByName(String name) {
        return buildingRepository.findByName(name);
    }



}
