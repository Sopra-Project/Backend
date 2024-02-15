package com.sopra.parkingsystem.repository;

import com.sopra.parkingsystem.model.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCodeRepository extends JpaRepository<UserCode, Integer> {
    UserCode findByCode(String code);
}
