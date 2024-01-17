package com.example.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiGatewayApp {

    public static void main(final String[] args) {
        SpringApplication.run(ApiGatewayApp.class, args);
    }

}
