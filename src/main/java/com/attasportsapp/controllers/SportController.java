package com.attasportsapp.controllers;

import com.attasportsapp.models.Sport;
import com.attasportsapp.models.dto.LocationsDTO;
import com.attasportsapp.models.dto.SportDTO;
import com.attasportsapp.repositories.LocationRepository;
import com.attasportsapp.repositories.SportRepository;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Sport> getSport(@PathVariable Long sportId) {
        try {
            Sport sport = sportRepository.findById(sportId).orElseThrow(NullPointerException::new);
            return ResponseEntity.ok(sport);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public List<Sport> getSports() {
        return sportRepository.findAll();
    }

    @GetMapping("/sorted")
    public List<LocationsDTO> getOrderedSports(
            @RequestBody List<SportDTO> sportsDTO,
            @RequestParam(defaultValue = "cost") String sortBy,
            @RequestParam(defaultValue = "01-01-1900") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(defaultValue = "01-01-2100") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {

//        List<Sport> sports = sportsDTO.stream().map(
//                sportDTO -> mapper.map(sportDTO, Sport.class)
//        ).collect(Collectors.toList());

        return service.getOrderedSportsInLocations(sportsDTO, startDate, endDate, sortBy);
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
    public ResponseEntity<?> deleteSport(@PathVariable Long sportId) {
        sportRepository.deleteById(sportId);
        return ResponseEntity.noContent().build();
    }
}
