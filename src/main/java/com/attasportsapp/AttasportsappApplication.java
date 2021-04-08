package com.attasportsapp;

import com.attasportsapp.controllers.CountryController;
import com.attasportsapp.controllers.CountyController;
import com.attasportsapp.controllers.LocationController;
import com.attasportsapp.models.Country;
import com.attasportsapp.models.County;
import com.attasportsapp.models.Location;
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

import java.util.Objects;

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

            Objects.requireNonNull(countryController.getCountryJPQL(country.getCountryId()).getBody()).getCounties().forEach(c -> System.out.println(c.getName()));
            Objects.requireNonNull(countyController.getCountyJPQL(county.getCountyId()).getBody()).getLocations().forEach(l -> System.out.println(l.getName()));
            if ((location = locationController.getLocationJPQL(location.getLocationId()).getBody()) != null) {
                System.out.println("Reached here");
                location.getSports().forEach(s -> System.out.println(s.getName()));
            }
        };
    }
}
