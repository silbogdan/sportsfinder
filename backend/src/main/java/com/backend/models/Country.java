package com.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    @Id @GeneratedValue
    private Long countryId;
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<County> counties;

    public Country(String name) {
        this.name = name;
    }

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    public Long getCountryId() {
        return this.countryId;
    }
}
