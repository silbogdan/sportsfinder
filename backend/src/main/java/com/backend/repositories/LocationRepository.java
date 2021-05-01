package com.backend.repositories;

import com.backend.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = "SELECT DISTINCT l FROM Location l JOIN FETCH l.sports as s WHERE s.name IN (:sportsNames) AND s.startDate BETWEEN :startDate AND :endDate ORDER BY s.cost")
    List<Location> findAllBetweenDates(@Param("sportsNames") List<String> sportsNames, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
