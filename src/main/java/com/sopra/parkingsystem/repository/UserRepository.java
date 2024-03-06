package com.sopra.parkingsystem.repository;

import com.sopra.parkingsystem.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    List<User> findByBuildingId(int id);

    @Transactional
    @Modifying
    @Query("update User u set u.name = :name, u.email = :email, u.role.id = :roleId where u.id = :id")
    int update(@Param("name") String name, @Param("email") String email, @Param("roleId") int roleId, @Param("id") int id);
}
