package com.attasportsapp.repositories;

import com.attasportsapp.models.Sport;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SportRepository extends JpaRepository<Sport, Long> {

    @Query(value = "from Sport s where s.startDate BETWEEN :startDate AND :endDate")
    List<Sport> findAllBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate, Sort sort);
}
