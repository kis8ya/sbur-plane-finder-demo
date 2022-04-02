package com.github.kis8ya.sburplanefinderdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class PlaneFinderService {

    private final AircraftRepository repository;

    public Flux<Aircraft> getAircraft() {
        return repository.findAll();
    }

}
