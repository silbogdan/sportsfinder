package com.backend.repositories;

import com.backend.models.Location;
import com.backend.models.Sport;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SportRepository extends JpaRepository<Sport, Long> {

    @Query(value = "from Sport s where s.name IN (:sportsNames) AND s.startDate BETWEEN :startDate AND :endDate")
    List<Sport> findAllBetweenDates(@Param("sportsNames") List<String> sportsNames, @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate, Sort sort);

    @Query(value = "from Sport s where s.location = :location AND s.name IN (:sportsNames) AND s.startDate BETWEEN :startDate AND :endDate")
    List<Sport> findAllBetweenDatesInLocation(@Param("location") Location location, @Param("sportsNames") List<String> sportsNames, @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate, Sort sort);
}
