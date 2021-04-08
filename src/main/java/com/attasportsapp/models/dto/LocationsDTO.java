package com.attasportsapp.models.dto;

import com.attasportsapp.models.Sport;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationsDTO {
    private String name;
    private List<Sport> sports;

    public LocationsDTO(String name) {
        this.name = name;
    }

    @JsonProperty("location")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("sports")
    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }
}
