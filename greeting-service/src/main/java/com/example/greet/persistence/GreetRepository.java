package com.example.greet.persistence;

import io.micrometer.observation.annotation.Observed;
import org.springframework.data.repository.CrudRepository;

@Observed( // does not work on an interface
        name = "greet.repository",  // metric name
        contextualName = "greet-repository" // span name
)
public interface GreetRepository extends CrudRepository<GreetEntity, Long> {
}
