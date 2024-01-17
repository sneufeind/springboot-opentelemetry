package com.example.greet.business;

import com.example.greet.persistence.GreetDbAdapter;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GreetService {

    private static final Random RANDOM = new Random();

    private final ObservationRegistry observationRegistry;
    private final GreetDbAdapter dbAdapter;

    @Autowired
    GreetService(
            final GreetDbAdapter dbAdapter,
            final ObservationRegistry observationRegistry
    ) {
        this.dbAdapter = dbAdapter;
        this.observationRegistry = observationRegistry;
    }

    public String greet(final String name) throws BusinessException {
        final var observation = Observation.createNotStarted("greeting", this.observationRegistry)
                .contextualName("Greet someone");

        try {
            observation.start();

            final var message = observation
                    .event(Observation.Event.of("message.creating", "Creating a message"))
                    .scoped(() -> new MessageGenerator().generate(name));

            observation.event(Observation.Event.of("message.created", "Message created"));

            if (RANDOM.nextInt(10) < 3) { // simulates server side exceptions
                throw new BusinessException("An unexpected error occured");
            }

            observation.scoped(() -> this.dbAdapter.save(message));
            observation.event(Observation.Event.of("message.persisted", "Message persisted"));

            return message;
        } finally {
            observation.stop();
        }
    }
}
