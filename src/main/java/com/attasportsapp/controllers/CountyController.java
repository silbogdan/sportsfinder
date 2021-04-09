package com.attasportsapp.controllers;

import com.attasportsapp.models.Country;
import com.attasportsapp.models.County;
import com.attasportsapp.repositories.CountryRepository;
import com.attasportsapp.repositories.CountyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@RestController
@RequestMapping("/api/counties")
public class CountyController {
    private final EntityManager entityManager;
    private final CountyRepository countyRepository;
    private final CountryRepository countryRepository;

    public CountyController(EntityManager entityManager, CountyRepository countyRepository, CountryRepository countryRepository) {
        this.entityManager = entityManager;
        this.countyRepository = countyRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/jpql/{countyId}")
    public ResponseEntity<County> getCountyJPQL(@PathVariable Long countyId) {
        try {
            County county = entityManager.createQuery(
                    "SELECT ct FROM County AS ct " +
                            "JOIN FETCH ct.locations " +
                            "WHERE ct.countyId = :id", County.class
            ).setParameter("id", countyId).getSingleResult();
            return ResponseEntity.ok(county);
        } catch (NoResultException e) {
            System.out.println("NoResultException: County " + countyId + " has an empy list of locations!");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{countyId}")
    public ResponseEntity<County> getCounty(@PathVariable Long countyId) {
        try {
            County county = countyRepository.findById(countyId).orElseThrow(NullPointerException::new);
            return ResponseEntity.ok(county);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<County>> getCountries() {
        return ResponseEntity.ok(countyRepository.findAll());
    }

    @PostMapping("/{countryId}")
    public ResponseEntity<County> addCountry(@RequestBody County county, @PathVariable(required = false) Long countryId) {
        if (countryId != null)
            county.setCountry(countryRepository.findById(countryId).orElseThrow());

        return new ResponseEntity<>(countyRepository.save(county), HttpStatus.CREATED);
    }

    @PutMapping("/{countyId}")
    public ResponseEntity<County> updateCountry(@RequestBody County county, @PathVariable Long countyId) {
        try {
            County updatedCounty = countyRepository.findById(countyId).map(
                    c -> {
                        c.setName(county.getName());
                        return c;
                    }
            ).orElseThrow(NullPointerException::new);
            return new ResponseEntity<>(countyRepository.save(updatedCounty), HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{countyId}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long countyId) {
        countyRepository.deleteById(countyId);
        return ResponseEntity.noContent().build();
    }
}
