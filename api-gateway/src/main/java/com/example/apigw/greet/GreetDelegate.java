package com.example.apigw.greet;

import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
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
        return this.client.greet(name);
    }
}
