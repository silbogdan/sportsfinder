package com.backend.controllers;

import com.backend.models.Country;
import com.backend.repositories.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final EntityManager entityManager;
    private final CountryRepository countryRepository;

    public CountryController(EntityManager entityManager, CountryRepository countryRepository) {
        this.entityManager = entityManager;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/jpql/{countryId}")
    public ResponseEntity<Country> getCountryJPQL(@PathVariable Long countryId) {
        try {
            Country country = entityManager.createQuery(
                    "SELECT ctr FROM Country AS ctr " +
                            "JOIN FETCH ctr.counties " +
                            "WHERE ctr.countryId = :id", Country.class
            ).setParameter("id", countryId).getSingleResult();
            return ResponseEntity.ok(country);
        } catch (NoResultException e) {
            System.out.println("NoResultException: Country " + countryId + " has an empty list of counties!");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<Country> getCountry(@PathVariable Long countryId) {
        try {
            Country country = countryRepository.findById(countryId).orElseThrow(NullPointerException::new);
            return ResponseEntity.ok(country);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Country>> getCountries() {
        return ResponseEntity.ok(countryRepository.findAll());
    }

    @PostMapping("")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        return new ResponseEntity<>(countryRepository.save(country), HttpStatus.CREATED);
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country, @PathVariable Long countryId) {

        try {
            Country updatedCountry = countryRepository.findById(countryId).map(
                    c -> {
                        c.setName(country.getName());
                        return c;
                    }
            ).orElseThrow(NullPointerException::new);
            return new ResponseEntity<>(countryRepository.save(updatedCountry), HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long countryId) {
        countryRepository.deleteById(countryId);
        return ResponseEntity.noContent().build();
    }
}
