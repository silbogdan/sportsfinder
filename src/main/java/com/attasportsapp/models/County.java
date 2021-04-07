package com.attasportsapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class County {

    @Id @GeneratedValue
    private Long countyId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference
    private Country country;

    @OneToMany(mappedBy = "county", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Location> locations;

    public County(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public County() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Long getCountyId() {
        return this.countyId;
    }
}
