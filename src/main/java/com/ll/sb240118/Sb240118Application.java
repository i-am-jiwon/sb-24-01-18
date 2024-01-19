package com.ll.sb240118;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Sb240118Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb240118Application.class, args);
    }

}
