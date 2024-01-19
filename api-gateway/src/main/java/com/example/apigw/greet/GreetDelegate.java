package com.example.apigw.greet;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreetDelegate implements GreetingApiClient {

    private final GreetingApiClient client;

    @Autowired
    GreetDelegate(final GreetingApiClient client){
        this.client = client;
    }

    @Observed(
            name = "greet.delegate"  // metric name
            , contextualName = "greet-delegate" // span name
//            , lowCardinalityKeyValues = {"rest-client"} // tags for metric & span
    )
    @Override
    public ResponseEntity<String> greet(String name) {
        log.info("Calling greeting service from delegate...");
        return this.client.greet(name);
    }
}
