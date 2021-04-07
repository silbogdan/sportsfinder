package com.attasportsapp.controllers;

import com.attasportsapp.models.Location;
import com.attasportsapp.repositories.CountyRepository;
import com.attasportsapp.repositories.LocationRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final EntityManager entityManager;
    private final LocationRepository locationRepository;
    private final CountyRepository countyRepository;

    public LocationController(EntityManager entityManager, LocationRepository locationRepository, CountyRepository countyRepository) {
        this.entityManager = entityManager;
        this.locationRepository = locationRepository;
        this.countyRepository = countyRepository;
    }

    @GetMapping("/jpql/{locationId}")
    public Location getLocationJPQL(@PathVariable Long locationId) { //TODO: Make proper HTTP Response
        try {
            return entityManager.createQuery(
                    "SELECT loc FROM Location AS loc " +
                            "JOIN FETCH loc.sports " +
                            "WHERE loc.locationId = :id", Location.class
            ).setParameter("id", locationId).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("NoResultException: Location " + locationId + " has an empty list of sports!");
            return null;
        }
    }

    @GetMapping("/{locationId}")
    public Location getLocation(@PathVariable Long locationId) {
        return locationRepository.findById(locationId).orElseThrow();
    }

    @GetMapping("")
    public List<Location> getCountries() {
        return locationRepository.findAll();
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
    public void deleteCountry(@PathVariable Long locationId) {
        locationRepository.deleteById(locationId);
    }

}
