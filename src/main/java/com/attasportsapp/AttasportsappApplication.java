package com.attasportsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class AttasportsappApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttasportsappApplication.class, args);
    }

}
