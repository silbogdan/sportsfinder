package com.attasportsapp.services;

import com.attasportsapp.models.Sport;
import com.attasportsapp.models.dto.SportDTO;
import com.attasportsapp.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportService {
    @Autowired
    SportRepository repository;

    public List<Sport> getOrderedSports(List<SportDTO> givenSports, LocalDate startDate, LocalDate endDate, String criteria) {
        Sort sortOrder = Sort.by(criteria);

        List<String> sportsNames = givenSports.stream().map(SportDTO::getName).collect(Collectors.toList());

        List<Sport> sports = repository.findAllBetweenDates(sportsNames, startDate, endDate, sortOrder);

        if (sports.isEmpty())
            return new ArrayList<>();
        else
            return sports;
    }
}
