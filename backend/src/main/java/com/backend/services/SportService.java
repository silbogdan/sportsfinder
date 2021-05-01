package com.backend.services;

import com.backend.models.Location;
import com.backend.models.Sport;
import com.backend.models.dto.LocationsDTO;
import com.backend.models.dto.SportDTO;
import com.backend.repositories.LocationRepository;
import com.backend.repositories.SportRepository;
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
    SportRepository sportRepository;

    @Autowired
    LocationRepository locationRepository;

    public List<Sport> getOrderedSports(List<SportDTO> givenSports, LocalDate startDate, LocalDate endDate, String criteria) {
        Sort sortOrder = Sort.by(criteria);

        List<String> sportsNames = givenSports.stream().map(SportDTO::getName).collect(Collectors.toList());

        List<Sport> sports = sportRepository.findAllBetweenDates(sportsNames, startDate, endDate, sortOrder);

        if (sports.isEmpty())
            return new ArrayList<>();
        else
            return sports;
    }

    // Solves the problem, but is not efficient (n+1 queries)
    public List<LocationsDTO> getOrderedSportsInLocations(List<SportDTO> givenSports, LocalDate startDate, LocalDate endDate, String criteria) {
        Sort sortOrder = Sort.by(criteria);

        List<String> sportsNames = givenSports.stream().map(SportDTO::getName).collect(Collectors.toList());

        List<Location> locations = locationRepository.findAll();
        List<LocationsDTO> locationsDTO = new ArrayList<>();

        locations.forEach(
                location -> {
                    LocationsDTO lDTO = new LocationsDTO(location.getName());
                    lDTO.setSports(sportRepository.findAllBetweenDatesInLocation(location, sportsNames, startDate, endDate, sortOrder));
                    locationsDTO.add(lDTO);
                }
        );



        if (locationsDTO.isEmpty())
            return new ArrayList<>();
        else
            return locationsDTO;
    }
}
