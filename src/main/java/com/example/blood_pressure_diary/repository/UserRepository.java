package com.example.blood_pressure_diary.repository;

import com.example.blood_pressure_diary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Query(value = "SELECT * FROM user_entity WHERE name= :username ", nativeQuery = true)
    UserEntity findByUsername(@Param("username") String username);
}
