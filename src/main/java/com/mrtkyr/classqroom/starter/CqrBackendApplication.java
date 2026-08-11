package com.mrtkyr.classqroom.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "com.mrtkyr.classqroom")
@ComponentScan(basePackages = "com.mrtkyr.classqroom")
@EnableJpaRepositories(basePackages = "com.mrtkyr.classqroom")
@EnableScheduling
public class CqrBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrBackendApplication.class, args);
    }
}
