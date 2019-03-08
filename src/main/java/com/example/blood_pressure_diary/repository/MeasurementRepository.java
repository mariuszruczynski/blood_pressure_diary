package com.example.blood_pressure_diary.repository;

import com.example.blood_pressure_diary.entity.MeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementEntity, Long> {


    @Query(value = "SELECT * FROM measurement_entity WHERE id=:id", nativeQuery = true)
    MeasurementEntity find(@Param("id") Long id);

    @Query(value = "SELECT * FROM measurement_entity WHERE date BETWEEN :start AND :end", nativeQuery = true)
    List<MeasurementEntity> findByDate(@Param("start") String startDate, @Param("end") String endDate);
}
