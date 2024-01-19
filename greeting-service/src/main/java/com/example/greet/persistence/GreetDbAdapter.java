package com.example.greet.persistence;

import com.example.greet.business.WaitUtil;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreetDbAdapter {

    private final GreetRepository repository;

    @Autowired
    GreetDbAdapter(final GreetRepository repository){
        this.repository = repository;
    }

    @Observed(
            name = "greet.persisting",  // metric name
            contextualName = "greet-persisting" // span name
    )
    public void save(final String message) {
        final var entity = new GreetEntity();
        entity.setMessage(message);
        log.info("Entity created and ready to be saved in database");
        this.repository.save(entity);
        log.info("Waiting for sync!");
        WaitUtil.waitInMilliseconds(200L); // simulates data connection
        log.info("Entity saved successfully!");
    }
}
