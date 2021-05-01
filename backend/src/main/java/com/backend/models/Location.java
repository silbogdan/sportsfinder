package com.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Location {
    @Id @GeneratedValue
    private Long locationId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "county_id")
    @JsonBackReference
    private County county;

    public Location(String name, County county) {
        this.name = name;
        this.county = county;
    }

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Sport> sports;

    public Location() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public List<Sport> getSports() {
        return sports;
    }
}
