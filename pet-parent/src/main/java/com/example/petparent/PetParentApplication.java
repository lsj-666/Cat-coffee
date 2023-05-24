package com.example.petparent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PetParentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetParentApplication.class, args);
    }

}
