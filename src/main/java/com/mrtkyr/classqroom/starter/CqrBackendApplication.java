package com.mrtkyr.classqroom.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.mrtkyr.classqroom")
@ComponentScan(basePackages = "com.mrtkyr.classqroom")
@EnableJpaRepositories(basePackages = "com.mrtkyr.classqroom")
public class CqrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrBackendApplication.class, args);
    }
}
