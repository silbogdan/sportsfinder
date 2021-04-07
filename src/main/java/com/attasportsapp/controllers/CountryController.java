package com.attasportsapp.controllers;

import com.attasportsapp.models.Country;
import com.attasportsapp.repositories.CountryRepository;
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
    public Country getCountryJPQL(@PathVariable Long countryId) { //TODO: Make proper HTTP Response
        try {
            return entityManager.createQuery(
                    "SELECT ctr FROM Country AS ctr " +
                            "JOIN FETCH ctr.counties " +
                            "WHERE ctr.countryId = :id", Country.class
            ).setParameter("id", countryId).getSingleResult();
        } catch (NoResultException e) {
            System.out.println("NoResultException: Country " + countryId + " has an empty list of counties!");
            return null;
        }
    }

    @GetMapping("/{countryId}")
    public Country getCountry(@PathVariable Long countryId) {
        return countryRepository.findById(countryId).orElseThrow();
    }

    @GetMapping("")
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @PostMapping("")
    public Country addCountry(@RequestBody Country country) {
        return countryRepository.save(country);
    }

    @PutMapping("/{countryId}")
    public Country updateCountry(@RequestBody Country country, @PathVariable Long countryId) {
        Country updatedCountry = countryRepository.findById(countryId).map(
                c -> {
                    c.setName(country.getName());
                    return c;
                }
        ).orElseThrow();

        return countryRepository.save(updatedCountry);
    }

    @DeleteMapping("/{countryId}")
    public void deleteCountry(@PathVariable Long countryId) {
        countryRepository.deleteById(countryId);
    }
}
