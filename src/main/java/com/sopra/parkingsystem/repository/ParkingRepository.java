package com.sopra.parkingsystem.repository;

import com.sopra.parkingsystem.model.ParkingSpot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSpot, Long> {

    @Modifying
    @Transactional
    @Query("update ParkingSpot p set p.status.id = 2 where p.id = :id")
    void unpark(@Param("id") int id);
}
