package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DemoApplication {


    // REMEMBER TO PREPARE A PROPER ARCHITECTURE TO
    // AVOID REWRITING WHOLE APPLICATION TO WORK WITH SOME NEW STANDARDS
    // NECESSARY ISOLATION OF HANDLING RENTINGS AND LENDINGS
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}


