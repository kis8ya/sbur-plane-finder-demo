package com.github.kis8ya.sburplanefinderdemo;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class PositionRetriever {

    private final AircraftRepository repository;

    @Bean
    Consumer<List<Aircraft>> retrieveAircraftPositions() {
        return acList -> {
            repository.deleteAll();
            repository.saveAll(acList);
            repository.findAll().forEach(System.out::println);
        };
    }
}
