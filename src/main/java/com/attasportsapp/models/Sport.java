package com.attasportsapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Sport {
    @Id @GeneratedValue
    private Long sportId;
    private String name;
    private Double cost;
    private java.sql.Date startDate;
    private java.sql.Date endDate;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference
    private Location location;

}
