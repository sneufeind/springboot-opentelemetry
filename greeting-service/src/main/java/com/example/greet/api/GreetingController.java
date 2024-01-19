package com.example.greet.api;

import com.example.greet.business.BusinessException;
import com.example.greet.business.GreetService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet")
@Slf4j
public class GreetingController {

    private final MeterRegistry meterRegistry;
    private final GreetService service;

    @Autowired
    GreetingController(
            final MeterRegistry meterRegistry,
            final GreetService service
    ) {
        this.meterRegistry = meterRegistry;
        this.service = service;
    }

    @GetMapping()
    ResponseEntity<String> greet(@RequestParam(value = "name", required = false) final String name) {
        log.info("Greet Request received!");
        this.meterRegistry.counter("greetings.total").increment();

        try {
            final var greet = this.service.greet(name);
            log.info("Greet Response: '{}'", greet);
            return ResponseEntity.ok(greet);
        } catch (final BusinessException e) {
            return ResponseEntity.internalServerError().body("Ooops");
        }
    }
}
