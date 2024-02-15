package com.sopra.parkingsystem.repository;

import com.sopra.parkingsystem.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    Building findByName(String name);

}
