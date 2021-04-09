package com.attasportsapp.controllers;

import com.attasportsapp.models.Location;
import com.attasportsapp.models.dto.SportDTO;
import com.attasportsapp.repositories.CountyRepository;
import com.attasportsapp.repositories.LocationRepository;
import com.attasportsapp.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final EntityManager entityManager;
    private final LocationRepository locationRepository;
    private final CountyRepository countyRepository;

    @Autowired
    LocationService service;

    public LocationController(EntityManager entityManager, LocationRepository locationRepository, CountyRepository countyRepository) {
        this.entityManager = entityManager;
        this.locationRepository = locationRepository;
        this.countyRepository = countyRepository;
    }

    @GetMapping("/jpql/{locationId}")
    public ResponseEntity<Location> getLocationJPQL(@PathVariable Long locationId) {
        try {
            Location location = entityManager.createQuery(
                    "SELECT loc FROM Location AS loc " +
                            "JOIN FETCH loc.sports " +
                            "WHERE loc.locationId = :id", Location.class
            ).setParameter("id", locationId).getSingleResult();
            return ResponseEntity.ok(location);
        } catch (NoResultException e) {
            System.out.println("NoResultException: Location " + locationId + " has an empty list of sports!");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocation(@PathVariable Long locationId) {
        try {
            Location location = locationRepository.findById(locationId).orElseThrow(NullPointerException::new);
            return ResponseEntity.ok(location);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public List<Location> getCountries() {
        return locationRepository.findAll();
    }

    @GetMapping("/sorted")
    public List<Location> getOrderedSportsInLocations(
            @RequestBody List<SportDTO> sportsDTO,
            @RequestParam(defaultValue = "01-01-1900") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam(defaultValue = "01-01-2100") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        return service.getOrderedSportsInLocations(sportsDTO, startDate, endDate);
    }

    @PostMapping("/{countyId}")
    public Location addLocation(@RequestBody Location location, @PathVariable(required = false) Long countyId) {
        if (countyId != null)
            location.setCounty(countyRepository.findById(countyId).orElseThrow());

        return locationRepository.save(location);
    }

    @PutMapping("/{locationId}")
    public Location updateLocation(@RequestBody Location location, @PathVariable Long locationId) {
        Location updatedLocation = locationRepository.findById(locationId).map(
                l -> {
                    l.setName(location.getName());
                    return l;
                }
        ).orElseThrow();

        return locationRepository.save(updatedLocation);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long locationId) {
        locationRepository.deleteById(locationId);
        return ResponseEntity.noContent().build();
    }

}
