package com.backend;

import com.backend.controllers.CountryController;
import com.backend.controllers.CountyController;
import com.backend.controllers.LocationController;
import com.backend.models.Country;
import com.backend.models.County;
import com.backend.models.Location;
import com.backend.repositories.CountryRepository;
import com.backend.repositories.CountyRepository;
import com.backend.repositories.LocationRepository;
import com.backend.repositories.SportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan
public class SportsfinderApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(SportsfinderApplication.class, args);
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

//            Objects.requireNonNull(countryController.getCountryJPQL(country.getCountryId()).getBody()).getCounties().forEach(c -> System.out.println(c.getName()));
//            Objects.requireNonNull(countyController.getCountyJPQL(county.getCountyId()).getBody()).getLocations().forEach(l -> System.out.println(l.getName()));
//            if ((location = locationController.getLocationJPQL(location.getLocationId()).getBody()) != null) {
//                System.out.println("Reached here");
//                location.getSports().forEach(s -> System.out.println(s.getName()));
//            }
        };
    }
}
