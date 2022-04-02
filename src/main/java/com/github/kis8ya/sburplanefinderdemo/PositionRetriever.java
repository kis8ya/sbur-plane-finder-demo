package com.github.kis8ya.sburplanefinderdemo;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class PositionRetriever {

    private final AircraftRepository repository;
    private final WebSocketHandler handler;

    @Bean
    Consumer<List<Aircraft>> retrieveAircraftPositions() {
        return acList -> {
            repository.deleteAll()
                            .thenMany(repository.saveAll(acList))
                                    .subscribe(args -> {
                                        System.out.println("Saved and gonna send data to ws...");
                                        sendPositions();
                                    });
        };
    }

    private void sendPositions() {
        for (WebSocketSession session : handler.getSessions()) {
            repository.findAll()
                    .collectList()
                    .subscribe(result -> {
                try {
                    System.out.println("Sending: " + result.size() + " positions...");
                    session.sendMessage(new TextMessage(result.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
