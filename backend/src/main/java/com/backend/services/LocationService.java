package com.backend.services;

import com.backend.models.Location;
import com.backend.models.dto.SportDTO;
import com.backend.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public List<Location> getOrderedSportsInLocations(List<String> givenSports, LocalDate startDate, LocalDate endDate) {

        return locationRepository.findAllBetweenDates(givenSports, startDate, endDate);
    }
}
