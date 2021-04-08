package com.attasportsapp;

import com.attasportsapp.controllers.CountryController;
import com.attasportsapp.controllers.CountyController;
import com.attasportsapp.controllers.LocationController;
import com.attasportsapp.models.Country;
import com.attasportsapp.models.County;
import com.attasportsapp.models.Location;
import com.attasportsapp.models.Sport;
import com.attasportsapp.repositories.CountryRepository;
import com.attasportsapp.repositories.CountyRepository;
import com.attasportsapp.repositories.LocationRepository;
import com.attasportsapp.repositories.SportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan
public class AttasportsappApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(AttasportsappApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CountryRepository countryRepository, CountyRepository countyRepository, LocationRepository locationRepository,
                                        SportRepository sportRepository, CountryController countryController, CountyController countyController,
                                        LocationController locationController) {
        return args -> {
            Country country = new Country("Romania");
            country = countryRepository.save(country);
            County county = new County("Cluj", country);
            county = countyRepository.save(county);
            Location location = new Location("Cheile Turzii", county);
            location = locationRepository.save(location);

            countryController.getCountryJPQL(country.getCountryId()).getCounties().forEach(c -> System.out.println(c.getName()));
            countyController.getCountyJPQL(county.getCountyId()).getLocations().forEach(l -> System.out.println(l.getName()));
            if ((location = locationController.getLocationJPQL(location.getLocationId())) != null) { // Checking NoResultException handler
                System.out.println("Reached here");
                location.getSports().forEach(s -> System.out.println(s.getName()));
            }
        };
    }
}
