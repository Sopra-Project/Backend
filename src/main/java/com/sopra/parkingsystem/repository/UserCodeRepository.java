package com.sopra.parkingsystem.repository;

import com.sopra.parkingsystem.model.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCodeRepository extends JpaRepository<UserCode, Integer> {
    List<UserCode> findByCode(String code);
}
