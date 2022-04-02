package com.github.kis8ya.sburplanefinderdemo;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.Instant;

@Configuration
public class DbContextInit {

    public ConnectionFactoryInitializer initializer(
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory
    ) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public CommandLineRunner init(AircraftRepository repository) {
        return args -> {
            repository.save(Aircraft.builder()
                            .callsign("SAL001")
                            .squawk("sqwk")
                            .reg("N12345")
                            .flightno("SAL001")
                            .route("route")
                            .type("LJ")
                            .category("ct")
                            .altitude(30000)
                            .heading(30)
                            .speed(300)
                            .vertRate(0)
                            .selectedAltitude(0)
                            .lat(38.7209228)
                            .lon(-90.4107416)
                            .barometer(0D)
                            .polarDistance(0D)
                            .polarBearing(0D)
                            .isADSB(false)
                            .isOnGround(false)
                            .lastSeenTime(Instant.now())
                            .posUpdateTime(Instant.now())
                            .bds40SeenTime(Instant.now())
                            .build())
                    .thenMany(repository.findAll())
                    .subscribe(System.out::println);
        };
    }
}
