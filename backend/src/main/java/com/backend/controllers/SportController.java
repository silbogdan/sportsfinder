package com.backend.controllers;

import com.backend.models.Sport;
import com.backend.models.dto.LocationsDTO;
import com.backend.models.dto.SportDTO;
import com.backend.repositories.LocationRepository;
import com.backend.repositories.SportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.backend.services.SportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sports")
public class SportController {
    private final SportRepository sportRepository;
    private final LocationRepository locationRepository;

    @Autowired
    SportService service;

    public SportController(SportRepository sportRepository, LocationRepository locationRepository) {
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
    public ResponseEntity<List<Sport>> getSports() {
        return ResponseEntity.ok(sportRepository.findAll());
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<LocationsDTO>> getOrderedSports(
            @RequestBody List<SportDTO> sportsDTO,
            @RequestParam(defaultValue = "01-01-1900") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(defaultValue = "01-01-2100") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
            @RequestParam(defaultValue = "cost") String sortBy) {

        return ResponseEntity.ok(service.getOrderedSportsInLocations(sportsDTO, startDate, endDate, sortBy));
    }

    @PostMapping(value = {"/{locationId}", ""})
    public ResponseEntity<Sport> addSport(@RequestBody Sport sport, @PathVariable(required = false) Long locationId) {
        if (locationId != null)
            sport.setLocation(locationRepository.findById(locationId).orElseThrow());

        return new ResponseEntity<>(sportRepository.save(sport), HttpStatus.CREATED);
    }

    @PutMapping("/{sportId}")
    public ResponseEntity<Sport> updateSport(@RequestBody Sport sport, @PathVariable Long sportId) {
        try {
            Sport updatedSport = sportRepository.findById(sportId).map(
                    s -> {
                        s.setName(sport.getName());
                        s.setCost(sport.getCost());
                        s.setStartDate(sport.getStartDate());
                        s.setEndDate(sport.getEndDate());
                        return s;
                    }
            ).orElseThrow(NullPointerException::new);
            return new ResponseEntity<>(sportRepository.save(updatedSport), HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{sportId}")
    public ResponseEntity<?> deleteSport(@PathVariable Long sportId) {
        sportRepository.deleteById(sportId);
        return ResponseEntity.noContent().build();
    }
}
