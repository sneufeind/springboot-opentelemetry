package com.example.apigw.greet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "greetingClient", url = "http://localhost:8081/greet")
public interface GreetingApiClient {

    @GetMapping()
    ResponseEntity<String> greet(@RequestParam(value = "name", required = false) final String name);

}
