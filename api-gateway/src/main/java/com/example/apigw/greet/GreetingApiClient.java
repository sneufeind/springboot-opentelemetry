package com.example.apigw.greet;

import io.micrometer.observation.annotation.Observed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "greetingClient", url = "http://localhost:8081/greet")
public interface GreetingApiClient {

    @Observed(
            name = "greet.client",
            contextualName = "greet-client"
    )
    @GetMapping()
    ResponseEntity<String> greet(@RequestParam(value = "name", required = false) final String name);

}
