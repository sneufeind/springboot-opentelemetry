package com.example.apigw.config;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jaeger")
public class JaegerConfig {

    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") final String tracingUrl) {
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(tracingUrl)
                .build();
    }
}
