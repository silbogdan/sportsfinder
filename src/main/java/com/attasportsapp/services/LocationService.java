package com.attasportsapp.services;

import com.attasportsapp.models.Location;
import com.attasportsapp.models.dto.SportDTO;
import com.attasportsapp.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public List<Location> getOrderedSportsInLocations(List<SportDTO> givenSports, LocalDate startDate, LocalDate endDate) {

        List<String> sportsNames = givenSports.stream().map(SportDTO::getName).collect(Collectors.toList());

        return locationRepository.findAllBetweenDates(sportsNames, startDate, endDate);
    }
}
