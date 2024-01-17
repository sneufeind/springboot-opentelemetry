package com.example.greet.business;

import io.micrometer.observation.annotation.Observed;

public class MessageGenerator {

    @Observed(
            name = "greet.generate",  // metric name
            contextualName = "greet-generating" // span name
    )
    public String generate(final String name) {
        WaitUtil.waitInMilliseconds(500L); // simulates message calculation
        return name == null || name.isBlank() ? "hello" : "hello %s!".formatted(name);
    }
}
