package com.attasportsapp.services;

import com.attasportsapp.models.Sport;
import com.attasportsapp.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SportService {
    @Autowired
    SportRepository repository;

    public List<Sport> getOrderedSports(LocalDate startDate, LocalDate endDate, String criteria) {
        Sort sortOrder = Sort.by(criteria);

        List<Sport> sports = repository.findAllBetweenDates(startDate, endDate, sortOrder);

        if (sports.isEmpty())
            return new ArrayList<>();
        else
            return sports;
    }
}
