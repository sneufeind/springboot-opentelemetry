package com.example.greet.business;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageGenerator {

    @Observed(
            name = "greet.generate",  // metric name
            contextualName = "greet-generating" // span name
    )
    public String generate(final String name) {
        log.info("Creating a message for '{}'", name);
        WaitUtil.waitInMilliseconds(500L); // simulates message calculation
        return name == null || name.isBlank() ? "hello" : "hello %s!".formatted(name);
    }
}
