package com.sopra.parkingsystem.repository;

import com.sopra.parkingsystem.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSpot, Long> {

}
