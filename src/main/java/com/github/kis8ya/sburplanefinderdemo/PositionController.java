package com.github.kis8ya.sburplanefinderdemo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequiredArgsConstructor
public class PositionController {

    @NonNull
    private final AircraftRepository repository;
    private final WebClient client = WebClient.create("http://localhost:7634/aircraft");

    @GetMapping({"/aircraft"})
    public String get(Model model) {
        repository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(aircraft -> !aircraft.getReg().isEmpty())
                .toStream()
                .forEach(repository::save);

        model.addAttribute("aircrafts", repository.findAll());

        return "positions";
    }
}
