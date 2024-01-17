package com.example.greet.persistence;

import com.example.greet.business.WaitUtil;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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
        this.repository.save(entity);
        WaitUtil.waitInMilliseconds(200L); // simulates data connection
    }
}
