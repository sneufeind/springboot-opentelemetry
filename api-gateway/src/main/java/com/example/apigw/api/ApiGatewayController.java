package com.example.apigw.api;

import com.example.apigw.greet.GreetDelegate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiGatewayController {

    private final GreetDelegate greetingDelegate;

    @Autowired
    ApiGatewayController(final GreetDelegate greetingDelegate) {
        this.greetingDelegate = greetingDelegate;
    }

    @GetMapping("/greet")
    ResponseEntity<String> greet(@RequestParam(value = "name", required = false) final String name) {
        final var greetResponse = this.greetingDelegate.greet(name);
        log.info("Ready to greet: '{}'", greetResponse.getBody());
        return greetResponse;
    }
}
