package com.attasportsapp.controllers;

import com.attasportsapp.models.Sport;
import com.attasportsapp.repositories.LocationRepository;
import com.attasportsapp.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.attasportsapp.services.SportService;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sports")
public class SportController {
    private final EntityManager entityManager;
    private final SportRepository sportRepository;
    private final LocationRepository locationRepository;

    @Autowired
    SportService service;

    public SportController(EntityManager entityManager, SportRepository sportRepository, LocationRepository locationRepository) {
        this.entityManager = entityManager;
        this.sportRepository = sportRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/{sportId}")
    public Sport getSport(@PathVariable Long sportId) {
        return sportRepository.findById(sportId).orElseThrow();
    }

    @GetMapping("")
    public List<Sport> getSports() {
        return sportRepository.findAll();
    }

    @GetMapping("/sorted")
    public List<Sport> getOrderedSports( //TODO: Make Sport DTO and add it as RequestBody
            @RequestParam(defaultValue = "cost") String sortBy,
            @RequestParam(defaultValue = "01-01-1900") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(defaultValue = "01-01-2100") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {

        return service.getOrderedSports(startDate, endDate, sortBy);
    }

    @PostMapping(value = {"/{locationId}", ""})
    public Sport addSport(@RequestBody Sport sport, @PathVariable(required = false) Long locationId) {
        if (locationId != null)
            sport.setLocation(locationRepository.findById(locationId).orElseThrow());

        return sportRepository.save(sport);
    }

    @PutMapping("/{sportId}")
    public Sport updateSport(@RequestBody Sport sport, @PathVariable Long sportId) {
        Sport updatedSport = sportRepository.findById(sportId).map(
                s -> {
                    s.setName(sport.getName());
                    s.setCost(sport.getCost());
                    s.setStartDate(sport.getStartDate());
                    s.setEndDate(sport.getEndDate());
                    return s;
                }
        ).orElseThrow();

        return sportRepository.save(updatedSport);
    }

    @DeleteMapping("/{sportId}")
    public void deleteSport(@PathVariable Long sportId) {
        sportRepository.deleteById(sportId);
    }
}
