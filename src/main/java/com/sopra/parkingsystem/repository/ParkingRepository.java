package com.sopra.parkingsystem.repository;

import com.sopra.parkingsystem.model.ParkingSpot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSpot, Long> {

    @Modifying
    @Transactional
    @Query("update ParkingSpot p set p.status.id = 2 where p.id = :id")
    void unpark(@Param("id") int id);

    @Query("select count(p) from ParkingSpot p " +
            "inner join Building b on p.user.building.id = :buildingId " +
            "and p.status.id = 1 " +
            "and p.startTime <= :time " +
            "and p.endTime >= :endTime")
    int getNumberOfParkedCars(@Param("buildingId") int buildingId, @Param("time") LocalDateTime date, @Param("endTime") LocalDateTime endTime);

    @Query("select p from ParkingSpot p where p.user.building.id = :buildingId")
    List<ParkingSpot> findByBuildingId(int buildingId);
}
