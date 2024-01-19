package com.example.greet.business;

import com.example.greet.persistence.GreetDbAdapter;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
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
        log.info("Before observation");
        final var observation = Observation.createNotStarted("greeting", this.observationRegistry)
                .contextualName("Greet someone");

        try {
            observation.start();
            log.info("Observation started!");

            final var message = observation
                    .event(Observation.Event.of("message.creating", "Creating a message"))
                    .scoped(() -> new MessageGenerator().generate(name));

            log.info("Message created: '{}'", message);
            observation.event(Observation.Event.of("message.created", "Message created"));

            if (RANDOM.nextInt(10) < 3) { // simulates server side exceptions
                throw new BusinessException("An unexpected error occured");
            }

            observation.scoped(() -> this.dbAdapter.save(message));
            observation.event(Observation.Event.of("message.persisted", "Message persisted"));
            log.info("Message saved to database!");

            return message;
        } finally {
            log.info("Observation will be stopped");
            observation.stop();
            log.info("After observation");
        }
    }
}
