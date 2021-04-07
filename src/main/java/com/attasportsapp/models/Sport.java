package com.attasportsapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Sport {
    @Id @GeneratedValue
    private Long sportId;
    private String name;
    private Double cost;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private java.sql.Date startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private java.sql.Date endDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference
    private Location location;

    public Sport(String name, Double cost, Date startDate, Date endDate, Location location) {
        this.name = name;
        this.cost = cost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public Sport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getSportId() {
        return this.sportId;
    }
}
