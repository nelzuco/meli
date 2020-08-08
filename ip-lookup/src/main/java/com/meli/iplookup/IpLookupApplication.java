package com.meli.iplookup;

import com.meli.iplookup.handler.ExternalApiErrorHandler;
import com.meli.iplookup.properties.UrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableConfigurationProperties(UrlProperties.class)
@EnableCaching
public class IpLookupApplication {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ExternalApiErrorHandler());

        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(IpLookupApplication.class, args);
    }
}