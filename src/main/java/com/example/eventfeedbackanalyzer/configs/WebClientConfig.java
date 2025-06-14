package com.example.eventfeedbackanalyzer.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${huggingface.api.token}")
    private String HFtoken;

    @Bean
    public WebClient APIwebClient() {
        return WebClient.builder()
                .baseUrl("https://router.huggingface.co/hf-inference/models/tabularisai/multilingual-sentiment-analysis")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + HFtoken, HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
